/**
 * 
 */
package com.seu.yang11.core;

import java.util.Date;

import android.app.Application;

import com.seu.yang11.core.domain.model.QuestionaireInstance;
import com.seu.yang11.core.domain.model.SurveyInfoCache;
import com.seu.yang11.core.domain.model.enums.QuestionaireStep;

/**
 * 整个应用的上下文，内存中存储整个调查问卷生命周期需要用到的临时变量
 * TODO 强化健壮性
 * 1. 如有一个标志位标识一个问题已经答过，不能重复答题
 * 
 * @author narutoying09@gmail.com
 * 
 */
public class AppContext extends Application {
	/**
	 * 当前进行中的调查问卷实例
	 */
	private QuestionaireInstance currentQuestionaireInstance;

	/**
	 * 当前进行的问题序号，该序号是指在问卷模板中所有问题列表中的序号
	 * 比如该模板有100道题，则序号从0-99
	 */
	private Integer currentQuestionIndex;

	/**
	 * 所有的题目数量
	 */
	private Integer numOfTotalQuestions;

	/**
	 * 当前所处的调查步骤
	 */
	private QuestionaireStep currentQuestionaireStep;

	/** 缓存最近一次调查中记录信息 */
	private SurveyInfoCache surveyInfoCache;
	/** 调查开始时间 */
	private Date surveyStartTime;

	/**
	 * 清空所有变量
	 */
	public void clear() {
		this.setCurrentQuestionaireInstance(null);
		this.setCurrentQuestionaireStep(null);
		this.setCurrentQuestionIndex(null);
		this.setNumOfTotalQuestions(null);
		this.setSurveyStartTime(null);
	}

	public Integer getNumOfTotalQuestions() {
		return numOfTotalQuestions;
	}

	public void setNumOfTotalQuestions(Integer numOfTotalQuestions) {
		this.numOfTotalQuestions = numOfTotalQuestions;
	}

	public QuestionaireInstance getCurrentQuestionaireInstance() {
		return currentQuestionaireInstance;
	}

	public void setCurrentQuestionaireInstance(
			QuestionaireInstance currentQuestionaireInstance) {
		this.currentQuestionaireInstance = currentQuestionaireInstance;
	}

	public Integer getCurrentQuestionIndex() {
		return currentQuestionIndex;
	}

	public void setCurrentQuestionIndex(Integer currentQuestionIndex) {
		this.currentQuestionIndex = currentQuestionIndex;
	}

	public QuestionaireStep getCurrentQuestionaireStep() {
		return currentQuestionaireStep;
	}

	public void setCurrentQuestionaireStep(
			QuestionaireStep currentQuestionaireStep) {
		this.currentQuestionaireStep = currentQuestionaireStep;
	}

	public SurveyInfoCache getSurveyInfoCache() {
		return surveyInfoCache;
	}

	public void setSurveyInfoCache(SurveyInfoCache surveyInfoCache) {
		this.surveyInfoCache = surveyInfoCache;
	}

	public Date getSurveyStartTime() {
		return surveyStartTime;
	}

	public void setSurveyStartTime(Date surveyStartTime) {
		this.surveyStartTime = surveyStartTime;
	}

}
