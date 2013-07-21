/**
 * 
 */
package com.seu.yang11.core.service;

import java.util.Date;

import android.content.Context;

import com.seu.yang11.common.dal.dao.QuestionaireInstanceDAO;
import com.seu.yang11.common.dal.dataobject.QuestionaireInstanceDO;
import com.seu.yang11.core.AppContext;
import com.seu.yang11.core.constants.Constants;
import com.seu.yang11.core.domain.model.OperationResult;
import com.seu.yang11.core.domain.model.QuestionAnswer;
import com.seu.yang11.core.domain.model.QuestionTemplate;
import com.seu.yang11.core.domain.model.QuestionaireBasicInfo;
import com.seu.yang11.core.domain.model.QuestionaireCompleteSign;
import com.seu.yang11.core.domain.model.QuestionaireInstance;
import com.seu.yang11.core.domain.model.QuestionaireTemplate;
import com.seu.yang11.core.domain.model.enums.QuestionaireStep;
import com.seu.yang11.core.repository.QuestionAnswerRepository;
import com.seu.yang11.core.repository.QuestionaireInstanceRepository;
import com.seu.yang11.core.repository.QuestionaireTemplateRepository;
import com.seu.yang11.core.repository.impl.QuestionAnswerRepositoryImpl;
import com.seu.yang11.core.repository.impl.QuestionaireInstanceRepositoryImpl;
import com.seu.yang11.core.repository.impl.QuestionaireTemplateRepositoryImpl;

/**
 * 本类负责调查问卷的生命周期
 * 开始->初始化->录入基本信息->问题答案录入(做题)->完成录入->后处理(做一些清理工作)
 * 
 * 注意：
 * 1. 从开始做题后，用户每次点击下一步按钮，都会通过上下文中存储的当前调查问卷id，将刚才填写的那一个题目
 * 的结果保存到数据库中，并对应当前调查问卷。
 * 
 * @author narutoying09@gmail.com
 *
 */
public class SurveyService {

	/**
	 * (1)
	 * 进行“初始化调查”操作，如生成此次调查的流水号(id，可以使用数据库自增id，或者其他方法生成一个唯一标示)、
	 * 调查创建时间等，并置入上下文中。
	 * 
	 * @param appContext
	 */
	public static void initSurvey(Context context, AppContext appContext) {
		appContext.clear();
		appContext.setSurveyStartTime(new Date());
		appContext.setCurrentQuestionaireStep(QuestionaireStep.INIT);
		// 初始化仓储对象
		QuestionaireTemplateRepository questionaireTemplateRepository = new QuestionaireTemplateRepositoryImpl(
				context);
		QuestionaireInstanceRepository questionaireInstanceRepository = new QuestionaireInstanceRepositoryImpl(
				context);
		// 获取当前问卷模板
		QuestionaireTemplate questionaireTemplate = questionaireTemplateRepository
				.getTemplateById(Constants.CURRENT_QUESTIONAIRE_TEMPLATE_ID);
		QuestionaireInstance questionaireInstance = questionaireInstanceRepository
				.createInstance(questionaireTemplate);
		appContext.setCurrentQuestionaireInstance(questionaireInstance);
		appContext.setNumOfTotalQuestions(questionaireTemplate
				.getQuestionTemplate().size());
		// 设置QuestionaireStep到(2)
		if (questionaireTemplate.getQuestionTemplate().size() == 0) {
			appContext.setCurrentQuestionaireStep(QuestionaireStep.TERMINATE);
		} else {
			appContext.setCurrentQuestionaireStep(QuestionaireStep.BASIC_INFO);
		}
	}

	/**
	 * (2)
	 * 录入基本信息
	 * 用户需要填写此次调查相关固有数据，如所属省、市、区、街道、区委会、门牌号等。
	 */
	public static OperationResult recordBasicInfos(Context context,
			AppContext appContext, QuestionaireBasicInfo basicInfo) {
		OperationResult result = new OperationResult();
		// 检查是否有重复问卷编号问题，若有则告知用户
		QuestionaireInstanceDAO questionaireInstanceDAO = new QuestionaireInstanceDAO(
				context);
		QuestionaireInstanceDO instanceDO = questionaireInstanceDAO
				.getByNumber(basicInfo.getNumber());
		if (instanceDO != null) {
			result.setMessage("问卷编号重复，请重新输入!");
			result.setSuccess(false);
		} else {
			// 初始化仓储对象
			QuestionaireInstanceRepository questionaireInstanceRepository = new QuestionaireInstanceRepositoryImpl(
					context);
			// 更新内存中的问卷实例
			QuestionaireInstance currentQuestionaireInstance = appContext
					.getCurrentQuestionaireInstance();
			if (currentQuestionaireInstance != null) {
				currentQuestionaireInstance
						.setQuestionnaireBasicInfo(basicInfo);
				// 更新数据库
				questionaireInstanceRepository
						.updateInstance(currentQuestionaireInstance);
				// 设置QuestionaireStep到(3)
				appContext
						.setCurrentQuestionaireStep(QuestionaireStep.QUESTIONS);
				appContext.setCurrentQuestionIndex(0);
			}
			result.setSuccess(true);
		}
		return result;
	}

	/**
	 * (3)
	 * 问题答案录入(做题)
	 * 接下来的就是调查问卷的题目，用户一一输入结果，每题占据一个屏幕，答完一个然后跳转到下一个
	 * 界面显示新的一题继续作答，一个题目由标题(title)与题体(content)组成，题目的类型有：
	 *   a). 填空题：
	 *   例子1：今年你____周岁，性别____
	 *   该例子中标题中就包含了填入框，题体为空，则该题的数据结构可表示为
	 *   title: 今年你$$周岁，性别$$
	 *   content: ""
	 *   而存在题目实例表中的值为
	 *   value: 10|男
	 *   
	 *   例子2：在日常生活中养成自觉保护环境的良好习惯需要哪些要求？
	 *          a.__________
	 *          b.__________
	 *          c.__________
	 *          d.__________
	 *          e.__________
	 *   
	 *   b). 判断题
	 *   例子：每个人都应该承担起维护自身和他人健康的责任。         
	 *         □ 是      □ 否
	 *   c). 单选题
	 *   例子1：您是哪个民族的？
	 *         ⑴汉族 ⑵回族 ⑶满族 ⑷维吾尔族 ⑸其他____
	 *   d). 多选题
	 *   例子1. 如何保持心理健康？
	 *          a. 情绪稳定，生活乐观   □
	 *          b. 适应环境变化		 □
	 *          c. 乐于工作，热爱生活  □
	 *          d. 正确处理人际关系     □
	 * 
	 * @param context
	 * @param appContext
	 * @param answers
	 * @param currentQuestionTemplate
	 * @param currentQuestionIndex
	 * @param nextQuestionIndex 
	 * 			一般情况下为null时代表下一题为按照原有顺序的下一题；
	 * 			当非null时，则代表下一题时本变量所指的那道题
	 */
	public static void recordQuestionAnswers(Context context,
			AppContext appContext, String[] answers,
			QuestionTemplate currentQuestionTemplate, int currentQuestionIndex,
			Integer nextQuestionIndex) {
		if (isLastQuestion(appContext)) {
			// 设置QuestionaireStep到(4)
			appContext.setCurrentQuestionaireStep(QuestionaireStep.COMPLETE);
		}
		// 持久化当前的问题答案
		QuestionAnswer answer = currentQuestionTemplate.buildAnswer(answers);
		if (answer != null) {
			QuestionAnswerRepository questionAnswerRepository = new QuestionAnswerRepositoryImpl(
					context);
			answer.setQuestionaireId(appContext
					.getCurrentQuestionaireInstance().getId());
			// 将当前问题答案放入数据库中
			questionAnswerRepository.saveQuestionAnswer(answer);
		}
		if (nextQuestionIndex == null) {
			appContext.setCurrentQuestionIndex(currentQuestionIndex + 1);
		} else {
			int allQuestionSizes = appContext.getCurrentQuestionaireInstance()
					.getQuestionnaireTemplate().getQuestionTemplate().size();
			int nextIndex = Math.min(allQuestionSizes - 1, nextQuestionIndex);
			appContext.setCurrentQuestionIndex(nextIndex);
		}
	}

	/**
	 * 判断是否是最后一题
	 * 
	 * @param appContext
	 * @return
	 */
	public static boolean isLastQuestion(AppContext appContext) {
		Integer numOfTotalQuestions = appContext.getNumOfTotalQuestions();
		if (appContext.getCurrentQuestionIndex() == numOfTotalQuestions - 1) {
			return true;
		}
		return false;
	}

	/**
	 * (4)
	 * 完成录入
	 * 当用户答完所有的问题之后，最后由调查员签字，输入姓名与调查日期，然后点击“完成调查”按钮即结束此次调查，
	 * 界面再跳转到首页
	 */
	public static void completeRecording(Context context,
			AppContext appContext, QuestionaireCompleteSign completeSign) {
		// 初始化仓储对象
		QuestionaireInstanceRepository questionaireInstanceRepository = new QuestionaireInstanceRepositoryImpl(
				context);
		QuestionaireInstance currentQuestionaireInstance = appContext
				.getCurrentQuestionaireInstance();
		if (currentQuestionaireInstance != null) {
			currentQuestionaireInstance
					.setQuestionaireCompleteSign(completeSign);
			questionaireInstanceRepository
					.updateInstance(currentQuestionaireInstance);
		}
		// 设置QuestionaireStep到(5)
		appContext.setCurrentQuestionaireStep(QuestionaireStep.POST_HANDLE);
	}

	/**
	 * (5)
	 * 后处理(做一些清理工作)
	 * 还需进行一系列的清理操作，比如将上下文中得各项数据重新初始化，以便进行下一次调查。
	 */
	public static void postSurvey(AppContext appContext) {
		// 一次调查问卷录入到此结束
		appContext.clear();
		appContext.setCurrentQuestionaireStep(QuestionaireStep.TERMINATE);
	}

}
