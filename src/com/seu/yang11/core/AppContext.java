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
 * ����Ӧ�õ������ģ��ڴ��д洢���������ʾ�����������Ҫ�õ�����ʱ����
 * TODO ǿ����׳��
 * 1. ����һ����־λ��ʶһ�������Ѿ�����������ظ�����
 * 
 * @author narutoying09@gmail.com
 * 
 */
public class AppContext extends Application {
	/**
	 * ��ǰ�����еĵ����ʾ�ʵ��
	 */
	private QuestionaireInstance currentQuestionaireInstance;

	/**
	 * ��ǰ���е�������ţ��������ָ���ʾ�ģ�������������б��е����
	 * �����ģ����100���⣬����Ŵ�0-99
	 */
	private Integer currentQuestionIndex;

	/**
	 * ���е���Ŀ����
	 */
	private Integer numOfTotalQuestions;

	/**
	 * ��ǰ�����ĵ��鲽��
	 */
	private QuestionaireStep currentQuestionaireStep;

	/** �������һ�ε����м�¼��Ϣ */
	private SurveyInfoCache surveyInfoCache;
	/** ���鿪ʼʱ�� */
	private Date surveyStartTime;

	/**
	 * ������б���
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
