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
 * ���ฺ������ʾ����������
 * ��ʼ->��ʼ��->¼�������Ϣ->�����¼��(����)->���¼��->����(��һЩ������)
 * 
 * ע�⣺
 * 1. �ӿ�ʼ������û�ÿ�ε����һ����ť������ͨ���������д洢�ĵ�ǰ�����ʾ�id�����ղ���д����һ����Ŀ
 * �Ľ�����浽���ݿ��У�����Ӧ��ǰ�����ʾ�
 * 
 * @author narutoying09@gmail.com
 *
 */
public class SurveyService {

	/**
	 * (1)
	 * ���С���ʼ�����顱�����������ɴ˴ε������ˮ��(id������ʹ�����ݿ�����id������������������һ��Ψһ��ʾ)��
	 * ���鴴��ʱ��ȣ��������������С�
	 * 
	 * @param appContext
	 */
	public static void initSurvey(Context context, AppContext appContext) {
		appContext.clear();
		appContext.setSurveyStartTime(new Date());
		appContext.setCurrentQuestionaireStep(QuestionaireStep.INIT);
		// ��ʼ���ִ�����
		QuestionaireTemplateRepository questionaireTemplateRepository = new QuestionaireTemplateRepositoryImpl(
				context);
		QuestionaireInstanceRepository questionaireInstanceRepository = new QuestionaireInstanceRepositoryImpl(
				context);
		// ��ȡ��ǰ�ʾ�ģ��
		QuestionaireTemplate questionaireTemplate = questionaireTemplateRepository
				.getTemplateById(Constants.CURRENT_QUESTIONAIRE_TEMPLATE_ID);
		QuestionaireInstance questionaireInstance = questionaireInstanceRepository
				.createInstance(questionaireTemplate);
		appContext.setCurrentQuestionaireInstance(questionaireInstance);
		appContext.setNumOfTotalQuestions(questionaireTemplate
				.getQuestionTemplate().size());
		// ����QuestionaireStep��(2)
		if (questionaireTemplate.getQuestionTemplate().size() == 0) {
			appContext.setCurrentQuestionaireStep(QuestionaireStep.TERMINATE);
		} else {
			appContext.setCurrentQuestionaireStep(QuestionaireStep.BASIC_INFO);
		}
	}

	/**
	 * (2)
	 * ¼�������Ϣ
	 * �û���Ҫ��д�˴ε�����ع������ݣ�������ʡ���С������ֵ�����ί�ᡢ���ƺŵȡ�
	 */
	public static OperationResult recordBasicInfos(Context context,
			AppContext appContext, QuestionaireBasicInfo basicInfo) {
		OperationResult result = new OperationResult();
		// ����Ƿ����ظ��ʾ������⣬�������֪�û�
		QuestionaireInstanceDAO questionaireInstanceDAO = new QuestionaireInstanceDAO(
				context);
		QuestionaireInstanceDO instanceDO = questionaireInstanceDAO
				.getByNumber(basicInfo.getNumber());
		if (instanceDO != null) {
			result.setMessage("�ʾ����ظ�������������!");
			result.setSuccess(false);
		} else {
			// ��ʼ���ִ�����
			QuestionaireInstanceRepository questionaireInstanceRepository = new QuestionaireInstanceRepositoryImpl(
					context);
			// �����ڴ��е��ʾ�ʵ��
			QuestionaireInstance currentQuestionaireInstance = appContext
					.getCurrentQuestionaireInstance();
			if (currentQuestionaireInstance != null) {
				currentQuestionaireInstance
						.setQuestionnaireBasicInfo(basicInfo);
				// �������ݿ�
				questionaireInstanceRepository
						.updateInstance(currentQuestionaireInstance);
				// ����QuestionaireStep��(3)
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
	 * �����¼��(����)
	 * �������ľ��ǵ����ʾ����Ŀ���û�һһ��������ÿ��ռ��һ����Ļ������һ��Ȼ����ת����һ��
	 * ������ʾ�µ�һ���������һ����Ŀ�ɱ���(title)������(content)��ɣ���Ŀ�������У�
	 *   a). ����⣺
	 *   ����1��������____���꣬�Ա�____
	 *   �������б����оͰ��������������Ϊ�գ����������ݽṹ�ɱ�ʾΪ
	 *   title: ������$$���꣬�Ա�$$
	 *   content: ""
	 *   ��������Ŀʵ�����е�ֵΪ
	 *   value: 10|��
	 *   
	 *   ����2�����ճ������������Ծ���������������ϰ����Ҫ��ЩҪ��
	 *          a.__________
	 *          b.__________
	 *          c.__________
	 *          d.__________
	 *          e.__________
	 *   
	 *   b). �ж���
	 *   ���ӣ�ÿ���˶�Ӧ�óе���ά����������˽��������Ρ�         
	 *         �� ��      �� ��
	 *   c). ��ѡ��
	 *   ����1�������ĸ�����ģ�
	 *         �ź��� �ƻ��� ������ ��ά����� ������____
	 *   d). ��ѡ��
	 *   ����1. ��α�����������
	 *          a. �����ȶ��������ֹ�   ��
	 *          b. ��Ӧ�����仯		 ��
	 *          c. ���ڹ������Ȱ�����  ��
	 *          d. ��ȷ�����˼ʹ�ϵ     ��
	 * 
	 * @param context
	 * @param appContext
	 * @param answers
	 * @param currentQuestionTemplate
	 * @param currentQuestionIndex
	 * @param nextQuestionIndex 
	 * 			һ�������Ϊnullʱ������һ��Ϊ����ԭ��˳�����һ�⣻
	 * 			����nullʱ���������һ��ʱ��������ָ���ǵ���
	 */
	public static void recordQuestionAnswers(Context context,
			AppContext appContext, String[] answers,
			QuestionTemplate currentQuestionTemplate, int currentQuestionIndex,
			Integer nextQuestionIndex) {
		if (isLastQuestion(appContext)) {
			// ����QuestionaireStep��(4)
			appContext.setCurrentQuestionaireStep(QuestionaireStep.COMPLETE);
		}
		// �־û���ǰ�������
		QuestionAnswer answer = currentQuestionTemplate.buildAnswer(answers);
		if (answer != null) {
			QuestionAnswerRepository questionAnswerRepository = new QuestionAnswerRepositoryImpl(
					context);
			answer.setQuestionaireId(appContext
					.getCurrentQuestionaireInstance().getId());
			// ����ǰ����𰸷������ݿ���
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
	 * �ж��Ƿ������һ��
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
	 * ���¼��
	 * ���û��������е�����֮������ɵ���Աǩ�֣�����������������ڣ�Ȼ��������ɵ��顱��ť�������˴ε��飬
	 * ��������ת����ҳ
	 */
	public static void completeRecording(Context context,
			AppContext appContext, QuestionaireCompleteSign completeSign) {
		// ��ʼ���ִ�����
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
		// ����QuestionaireStep��(5)
		appContext.setCurrentQuestionaireStep(QuestionaireStep.POST_HANDLE);
	}

	/**
	 * (5)
	 * ����(��һЩ������)
	 * �������һϵ�е�������������罫�������еø����������³�ʼ�����Ա������һ�ε��顣
	 */
	public static void postSurvey(AppContext appContext) {
		// һ�ε����ʾ�¼�뵽�˽���
		appContext.clear();
		appContext.setCurrentQuestionaireStep(QuestionaireStep.TERMINATE);
	}

}
