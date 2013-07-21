/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2012 All Rights Reserved.
 */
package com.seu.yang11.common.util;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

import com.seu.yang11.common.dal.DBConfigs;

/**
 * sqlite���ݿ�����
 * 
 * @author cheng.dai@alipay.com
 * @version $Id: DataBaseOpenHelper.java, v 0.1 2012-4-25 ����9:07:54 narutoying Exp $
 */
public class DataBaseOpenHelper extends SQLiteOpenHelper {
	// ��û��ʵ����,�ǲ����������๹�����Ĳ���,��������Ϊ��̬  
	private static String dbname = DBConfigs.QUESTIONAIRE_DB_NAME;
	private static int version = 1;

	public DataBaseOpenHelper(Context context) {
		// ��һ��������Ӧ�õ�������  
		// �ڶ���������Ӧ�õ����ݿ�����  
		// ����������CursorFactoryָ����ִ�в�ѯʱ���һ���α�ʵ���Ĺ�����,����Ϊnull,����ʹ��ϵͳĬ�ϵĹ�����  
		// ���ĸ����������ݿ�汾�������Ǵ���0��int�����Ǹ�����  
		super(context, dbname, null, version);
	}

	public DataBaseOpenHelper(Context context, String name,
			CursorFactory factory, int version) {
		super(context, name, factory, version);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		/**
		 * ����������ڵ�һ�γ�ʼ�����ݿ��ʱ����õģ��𵽴���4�ű��ͳ�ʼ��һЩ���ݣ���Ҫ��������ģ�壩
		 * һ������֮�󣬾Ͳ����ٵ�����������ˣ���Ϊÿ�γ���������ݿ��ʱ�򶼻�ʹ������࣬��������Ȼ�ȥ
		 * ���� dbname = "DietaryExposureSystem.db"���Ƿ��Ѿ��������db�ļ����������������ļ���
		 * �ͻ����onCreate������������Ե���һ�κ������db�ļ����Ͳ����ٳ�ʼ������������~
		 */
//		initQuestionaireTemplate(db);
//		initQuestionaireInstance(db);
//		initQuestionTemplate(db);
//		initQuestionInstance(db);
	}

	private void initQuestionaireTemplate(SQLiteDatabase db) {
		db.execSQL(DBConfigs.CREATE_TABLE_QUESTIONAIRE_TEMPLATE);
		db.execSQL("insert into "
				+ DBConfigs.TABLE_QUESTIONAIRE_TEMPLATE
				+ " (id,name,description,questionsteps) values(1,'ʡ���ص����ʾ�','ʡ���ص����ʾ�','0:���Բ���0|1:���Բ���1|2:���Բ���2|3:���Բ���3|4:���Բ���4')");
	}

	private void initQuestionaireInstance(SQLiteDatabase db) {
		db.execSQL(DBConfigs.CREATE_TABLE_QUESTIONAIRE_INSTANCE);
	}

	private void initQuestionTemplate(SQLiteDatabase db) {
		db.execSQL(DBConfigs.CREATE_TABLE_QUESTION_TEMPLATE);
		// ��ʼ����Ŀ�����ô���ķ�ʽ��ʼ�����Ժ����Ƿ����ɿ���ͨ���ļ�������Ŀ�Ĺ��� TODO
		// ��Ŀ���ֶ�����Ϊ��
		// �ʾ�ģ��id(Ĭ��1)�������ڲ����(��1��ʼ��ÿ�����͵������ڲ�����໥����)��
		// ��ɣ����壬����(QuestionStep)������(QuestionType)
		String basicSql = "insert into "
				+ DBConfigs.TABLE_QUESTION_TEMPLATE
				+ " (questionaireTemplateId, qIndex, title, content, step, type)"; // validator
		//������Ϣ
		/*		db.execSQL(basicSql
						+ " values('1', '1', '�����Ա�?', '1@��|2@Ů', '0', '3');");
		db.execSQL(basicSql
				+ " values('1', '2', '�����꼸����?$����������?$', '', '0', '1', 'digit:0-100|digit:0-60');");
		// ����ʹ��
		db.execSQL(basicSql
				+ " values('1', '2', '���꼸����?$��������?$', '', '1', '1', '');");
		db.execSQL(basicSql
				+ " values('1', '3', '�����ĸ�����ģ�', '1@����|2@����|3@����|4@ά�����|5@�ɹ���|6@����', '0', '3');");
		db.execSQL(basicSql
				+ " values('1', '4', '���������Ļ��̶���Σ�', '1@��ʶ�ֻ�ʶ�ֺ��� |2@Сѧ |3@����|4@����/ְ��/��ר|5@��ר/����|6@˶ʿ������', '0', '3');");
		db.execSQL(basicSql
				+ " values('1', '5', '�����ڵ�ְҵ��ʲô��', '1@���һ��ء���Ⱥ��֯������|2@����ҵ��λ������ |3@רҵ������Ա|4@������Ա���й���Ա|5@ũ���֡������桢ˮ��ҵ������Ա|6@��ҵ�ͷ���ҵ��Ա|7@�����������豸��������" +
				"|8@ѧ��|9@ʧҵ��Ա���¸���Ա|10@��������Ա |11@ũ��|12@����', '0', '3');");
		db.execSQL(basicSql
				+ " values('1', '6', '�����ڵĻ���״����ʲô��', '1@δ��|2@�ѻ�|3@ɥż|4@����|5@����', '0', '3');");
		db.execSQL(basicSql
				+ " values('1', '7', '���Ƿ����ҽ����������Ĺ���', '1@��|2@��', '0', '3');");
		db.execSQL(basicSql + " values('1', '8', '���ļ�ͥ��ס�˿ڼ��ˣ�ָÿ���ڼҾ�ס�������ϣ�$', '', '0', '1');");
		db.execSQL(basicSql
				+ " values('1', '9', 'ȥ�꣬��ȫ�ҵ�ƽ���������Ƕ��٣�', '1@<500Ԫ|2@500��999Ԫ|3@1000��1999Ԫ|4@2000��4999Ԫ|5@5000��9999Ԫ|6@10000����|6@�����', '0', '3');");
		
		
		//�ж��ⲿ��
		db.execSQL(basicSql
				+ " values('1', '1', 'ÿ���˶�Ӧ�е���ά�����������˽��������Ρ�', '1@��|0@��', '1', '2');");
		db.execSQL(basicSql
				+ " values('1', '2', '������ָ�޼�����������������״����', '1@��|0@��', '1', '2');");
		db.execSQL(basicSql
				+ " values('1', '3', '��������ʳ�����򣬰��������½��ǰ�֢�����ڱ����źš�', '1@��|0@��', '1', '2');");
		db.execSQL(basicSql
				+ " values('1', '4', '����������ƣ����󲿷ַν�˲����ܹ�������', '1@��|0@��', '1', '2');");
		db.execSQL(basicSql
				+ " values('1', '5', 'ˮ����Ӫ���ɷֺͽ���ЧӦ������߲��кܶ�����֮��������ˮ�����Դ����߲ˡ�', '1@��|0@��', '1', '2');");
		db.execSQL(basicSql
				+ " values('1', '6', '�Ѻá����ӿ��롱�أ���Ԥ���׸Ρ����֡����򲡵ȼ���������Ч��ʩ��', '1@��|0@��', '1', '2');");
		db.execSQL(basicSql
				+ " values('1', '7', '�������˵�����ѪѹΪ����ѹ����140���׹���������ѹ����90���׹�����', '1@��|0@��', '1', '2');");
		db.execSQL(basicSql
				+ " values('1', '8', '����Ʒ���ܴ���ҩƷ�����������ʳƷ��', '1@��|0@��', '1', '2');");
		db.execSQL(basicSql
				+ " values('1', '9', '����д��ʱ���۾����鱾��Ѿ���Ϊ40���ס�', '1@��|0@��', '1', '2');");
		db.execSQL(basicSql
				+ " values('1', '10', 'ż���Ե㡰ҡͷ�衱�ȶ�Ʒ�����񫣬������Ҳ�޺���', '1@��|0@��', '1', '2');");
		db.execSQL(basicSql
				+ " values('1', '11', '����ȳ�ҩ�õÿ죬�����˾�Ӧ�þ������롣', '1@��|0@��', '1', '2');");
		db.execSQL(basicSql
				+ " values('1', '12', '���������60���Ժ�Ž��̾����ˣ��Խ���û���洦��', '1@��|0@��', '1', '2');");
		db.execSQL(basicSql
				+ " values('1', '13', '�Ͷ���Ӧ���˽⹤����λ���ڵ�Σ�����أ����ز�����̣�ע����˷�������������ϰ�ߡ�', '1@��|0@��', '1', '2');");
		
		*/
		//��ѡ��
		db.execSQL(basicSql
				+ " values('1', '1', '�����Ķ�����ָ��', '1@����û�в�@3|2@���ֲ��ݣ�����ǿ׳@4|3@�ܳ���˯�޴�@5|4@���彡�����������������õ������Ӧ����|5@��֪��', '2', '3');");
		db.execSQL(basicSql
				+ " values('1', '2', '����Ϊ�������ڿ�����Ⱦ����Ч�ķ����ǣ�', '1@��������ͨ�� |2@����������|3@�����������¼�|4@���϶���|5@��֪��', '2', '3');");
		db.execSQL(basicSql
				+ " values('1', '3', '����Ч����õ�ʳƷ������ʳƷ��', '1@����|2@����|3@����|4@�߲�|5@��֪��', '2', '3');");
		db.execSQL(basicSql
				+ " values('1', '4', '�ҹ������˽������ص�����ָ����BMI����ΧΪ��', '1@14.5��18.4ǧ��/��2|2@18.5��23.9ǧ��/��2|3@24��27.9ǧ��/��2|4@28��32.9ǧ��/��2|5@��֪��', '2', '3');");
		db.execSQL(basicSql
				+ " values('1', '5', '����������ƽ������ʱ��ÿ���Ӻ�������Ϊ��', '1@12-14��|2@14-16��|3@16-20��|4@20-22��|5@��֪��', '2', '3');");
		db.execSQL(basicSql
				+ " values('1', '6', '��ͯ����������������õ�����ϰ�ߣ���������д���˳����೤ʱ�䣿', '1@30����|2@50����|3@1Сʱ|4@90����|5@��֪��', '2', '3');");
		db.execSQL(basicSql
				+ " values('1', '7', '�и������ڻ��ж೤ʱ���ڣ���ʼ�������ڼ��?', '1@3������|2@4������|3@5������|4@6������|5@��֪��', '2', '3');");
		db.execSQL(basicSql
				+ " values('1', '8', '������ʳ�Խ��������ЩΣ����', '1@�����𳦵���Ⱦ��|2@������β�|3@�������Ѫѹ�����ಡ������Ӳ��|4@���϶���|5@��֪��', '2', '3');");
		db.execSQL(basicSql
				+ " values('1', '9', '��������������ʲô������', '1@����ƾ��ж�|2@�����ӻ���Ӳ������������֢����Ѫѹ���з�ȼ����ķ��ա�|3@�׵��������˺�|4@���϶���|5@��֪��','2','3');");
			/*	
		db.execSQL(basicSql
				+ " values('1', '10', '�����������˲�С��ˤ����ʱ��Ӧ��δ�����', '1@�������ȥҽԺ|2@�̶���֫���ѻ���̧��ҽԺ����|3@�û����Դ���Ϣ|4@��֪����δ��� ', '2', '3');");
		db.execSQL(basicSql
				+ " values('1', '11', '���Ѫѹ�ķ�����ϵ�����е��ǣ�', '1@���֡����ι��ࡢ������ŵ�|2@ƶѪ��ѪҺŨ�ȹ���|3@Ӫ������|4@��֪��', '2', '3');");
		db.execSQL(basicSql
				+ " values('1', '12', '�ν�˲���Ҫͨ������;��������', '1@�ճ�����Ӵ�����|2@���ԡ�������Ȳ����ķ�ĭ����|3@�ԽӴ�����|4@��֪��', '2', '3');");
		db.execSQL(basicSql
				+ " values('1', '13', 'ĿǰԤ�����͸�������Ч�Ĵ�ʩ��', '1@��ǿ�����������ò;����������Բ���ʳƷ��|2@ȫ����������򵰰�|3@���ĸӤ����|4@���ⲻ��ȫ����Ϊ|5@��֪��', '2', '3');");
		db.execSQL(basicSql
				+ " values('1', '14', '�������̵�Ƿ�ֹ�����������ּ�������Ч��ʩ��', '1@����|2@��Ѫѹ|3@�ν��|4@����|5@��֪��', '2', '3');");
		db.execSQL(basicSql
				+ " values('1', '15', 'Ԥ�����̲��Ĵ��󷽷��ǣ�', '1@��ȫ����Ϊ����ȷʹ�������ɿ��ı����ף������԰���|2@Զ�붾Ʒ�������������䲻�����˹������������|3@���⾭Ѫ��Ⱦ���ᳫ�޳���Ѫ�������뱻��Ⱦ��ѪҺ��|4@Զ�밬�̲���Ⱦ�ߺͰ��̲����ߣ��������ǽӴ���', '2', '3');");
		db.execSQL(basicSql
				+ " values('1', '16', '����ΪԤ��һЩ��Ⱦ�����磺����Ҹεȣ�����Ч����õĴ�ʩ�ǣ�', '1@��ǿӪ��|2@��������|3@ĸ��ι��|4@��������|5@ע���������|6@��֪��', '2', '3');");
		db.execSQL(basicSql
				+ " values('1', '17', '�������ͬѧ��ͬ�»����ѵ��˷ν����������Դ�����', '1@����ν���������佻��|2@�Ӵ˲��ٺ�������|3@ֻҪ����һ��ʱ����������ƺ�Ϳ�����Դ�������һ��|4@��֪��', '2', '3');");
		db.execSQL(basicSql
				+ " values('1', '18', '����Ϊ�������޳���Ѫ����ָÿ��ÿ����Ѫ���ٺ�����', '1@С��200����|2@200��400����|3@����400����|4@��֪��', '2', '3');");
		db.execSQL(basicSql
				+ " values('1', '19', '���¹��ڿ����صļ���˵��������Ϊ��ȷ���ǣ�', '1@��ð�������ϳԿ�����|2@�����ؿ��Ը��ݲ��飬���й���|3@������Ӧ��ҽ��ָ����ʹ��|4@�����ؼ���ɱ��ϸ������ɱ�𲡶�|5@��֪��', '2', '3');");
		db.execSQL(basicSql
				+ " values('1', '20', '����Ϊ��Ѫ���没����Ϊ��', '1@�Բ��ɾ�ʳ��|2@����ˮ|3@���ж��ݵĵط���ˮ����ˮ����Ӿ|4@��֪��', '2', '3');");
		db.execSQL(basicSql
				+ " values('1', '21', '����Ϊ����˵����ȷ���ǣ�', '1@������ʳƷҪ�ֿ���źͼӹ�|2@�����߲˵ĵ�����ˮ������������|3@���������壬��ˮ������������|4@��֪��', '2', '3');");
		db.execSQL(basicSql
				+ " values('1', '22', 'Ϊ�˱�֤��ʢ�ľ�������߹���Ч�ʣ����²���ȷ���ǣ�', '1@�������õ�ѧϰ������������ϰ�ߣ�|2@�����μ�������|3@˯��Խ��Խ�ã�|4@���ݽ�ϡ�', '2', '3');");
		
		//��ѡ��
		db.execSQL(basicSql
				+ " values('1', '1', '��α�������������', 'a@�����ȶ�������̬���ֹۣ�|b@��Ӧ�����仯��|c@���ڹ������Ȱ����|d@��ȷ�����˼ʹ�ϵ��|e@����Խ��Խ�ã����ۻ�Ⱦƣ�|f@��֪��', '3', '4');");
		db.execSQL(basicSql
				+ " values('1', '2', '����Ϊ���������ʽ������Щ���ݣ�', 'a@���̡��޾�|b@ƽ����ʳ|c@���ؿ���|d@��Ա���Ʒ��Ӫ��Ʒ|e@��������|f@��Զ�˯|g@�����˶�|h@��֪��', '3', '4');");
		db.execSQL(basicSql
				+ " values('1', '3', '�Դ�����ν���������', 'a@�жϵ�Դ��|b@�þ�Ե���������ߣ�|c@�Ѵ������Ƶ�ͨ�紦���ȣ�|d@�����ƿ�������Ա��|e@�ø���Ĺ��Ӳ��봥����Ա��|f@��֪��', '3', '4');");
		db.execSQL(basicSql
				+ " values('1', '4', '����Ϊ�������̻�����������Щ����?', 'a@�ΰ�|b@���Ĳ�|c@���������Էβ�|d@ȣ��|e@θ����|f@������|g@����|h@��������|i@��֪��', '3', '4');");
		db.execSQL(basicSql
				+ " values('1', '5', '��ͯ�ƻ�����(Ԥ��ע��)��Ԥ����Щ������', 'a@�ٰ��������Ԥ�������տȡ��׺������˷磻|b@�������Ԥ����˲���|c@���������Ԥ��С����ԣ�|d@���������Ԥ�����|e@�Ҹ������Ԥ���ҸΣ�|f@��֪��', '3', '4');");
		db.execSQL(basicSql
				+ " values('1', '6', 'ĸ��ι����Ӥ��������������ʲô�ô���', 'a@ĸ��Ӫ��ȫ�桢�����������գ�|b@���ã�|c@ĸ�麬�п��壬��ǿӤ���Ŀ���������|d@���㣻|e@�¶����ˡ���ࣻ|f@��֪��', '3', '4');");
		db.execSQL(basicSql
				+ " values('1', '7', 'ʳ���μӵ�ĺô��ǣ�', 'a@Ԥ��ȱ���Լ�״���ף��׳ƴ��Ӳ���|b@Ԥ��̥�����ͯ����������|c@���������������������Ҫ��|d@Ԥ����ͯ��������;|e@Ԥ����Ѫѹ��|f@��֪��', '3', '4');");
		db.execSQL(basicSql
				+ " values('1', '8', '��������ú���ж�ʱ������Ӧ������������', 'a@���ж��ߺ�ˮ|b@���Ŵ�ͨ��|c@���콫�ж���ת�Ƶ��������ʡ�ͨ�����õĵط������ر�ȼ���ܿ���|d@���ÿ�����|e@��ú���ж���̧�����ĵط����²����|f@��֪��', '3', '4');");
		db.execSQL(basicSql
				+ " values('1', '9', '�ҸκͰ��̲��Ĺ�ͬ�Ĵ���;������Щ��', 'a@�ճ�����Ӵ�|b@ѪҺ����|c@����������|d@�ԽӴ�����|e@�������뵶����ˢ|f@ĸӤ����|g@�����Ͷ�����|h@��֪��', '3', '4');");
		db.execSQL(basicSql
				+ " values('1', '10', '����ë���ɴ����ļ����У�', 'a@Ƥ����|b@�Դ�������|c@���۲���ɳ��|d@��ǻ����|e@��ʹ|f@��֪��', '3', '4');");
		db.execSQL(basicSql
				+ " values('1', '11', 'Ϊ���ⷢ��ũҩ�ж�����ָ��������ȷ��˵����', 'a@����ҩҺ�Ͱ���ʱҪ����������|b@���ҩҺ�����·�����Ҫ�����·�|c@��������ũҩ���ѡ������2��3����|d@����ũҩ�����в��ܺ�ˮ������|e@�����ũҩ�ɵ���ˮ����|f@��֪��', '3', '4');");
		db.execSQL(basicSql
				+ " values('1', '12', '���в���ͨ����Щ;��������', 'a@���ԡ������硢˵��ʱ�����ķ�ĭ|b@�����в�����Ⱦ����|c@�����в�����Ⱦ�Ĳ�ߡ�ʳ�ߡ�ë��|d@�������ˮ|e@�����ʳ��|f@�벡�˵�ֱ�ӽӴ�|g@��֪��', '3', '4');");
		db.execSQL(basicSql
				+ " values('1', '13', 'ְҵ�Խ���������', 'a@�ϸ�ǰ���|b@�ڸ��ڼ䶨�����|c@��ڽ������|d@Ӧ���������|e@���ڽ������|f@��֪��', '3', '4');");
		db.execSQL(basicSql
				+ " values('1', '14', '����ȱ��������Щ������', 'a@������������|b@��С֢|c@�и�����|d@��Ѫѹ|e@����|f@��֪��', '3', '4');");
		db.execSQL(basicSql
				+ " values('1', '15', '�˱�����èҧ�˺�Ӧ��ȡ������Щ��ʩ��', 'a@�����÷���ˮ����ˮ���׳�ϴ�˿�����15����|b@�����ÿڽ�Ѫˮ˱������|c@��2%��3%�ĵ�ƻ�75%�ľƾ�Ϳ�������˿�|d@ע���Ȯ�������ͬʱע�俹��Ȯ��Ѫ��|e@��ְ�ʱ����5���Ȯ����|f@���������Ժ���֢״����ֹͣ��������|g@��֪��', '3', '4');");
		db.execSQL(basicSql
				+ " values('1', '16', '���غͷ�����������Щ�������������ӵ���Ҫԭ��֮һ��', 'a@����Ѫ�ܲ�|b@����|c@�׿�|d@֬����|e@ʹ��|f@��֪��', '3', '4');");
		db.execSQL(basicSql
				+ " values('1', '17', 'ʲô���Ķ��Ͱ�װʳƷ��Ҫ����', 'a@����������|b@�޳������ơ���ַ|c@����������|d@�ʹ�ʳƷ|e@��ʳƷ��׼�ĺ�|f@��֪��', '3', '4');");
		db.execSQL(basicSql
				+ " values('1', '18', '���ӡ���Ӭ�����������Դ������ּ���������Ϊ���Ƶ��ۺϴ�ʩ����Щ�� ', 'a@����������|b@�����Ϳ��������أ�|c@ֱ����ɱ��|d@ʹ��ũҩֱ����ɱ��|e@���ط���ס��|f@��֪��', '3', '4');");
		db.execSQL(basicSql
				+ " values('1', '19', '�����ʹ��ũҩ��ʱ������Ϊ������Щ��������ȷ�ģ�', 'a@����ũҩʱ�����Ժ��ճ���Ʒ����һ������(������)��|b@ũҩ����Զ��С���ĵط�|c@ũҩ����ƾ����ʹ��|d@��ʳũҩ�������ж�������Ӧ�������Ǵ���|e@ũҩ���ܷ��ڳ���|f@��֪��', '3', '4');");
		db.execSQL(basicSql
				+ " values('1', '20', '����������ǰ����ʱ������ȷ��������', 'a@��֮��֮|b@�Լ��㿪|c@����ͨ��|d@Ȱ��������|e@������һ������|f@��֪��', '3', '4');");
		db.execSQL(basicSql
				+ " values('1', '21', 'Ϊ�˶�����ͥ���˸��������ЧԤ����·��ͨ�˺���', 'a@���������߳˳���ʱ��һ��Ҫ���ս�ͨ����Ĺ涨ϵ�ð�ȫ�������ͷ��|b@�����ټ�ʻ����ƣ�ͼ�ʻ�����ƺ�ݳ���|c@��Ħ�г������ͷ����|" +
				"d@���˺ͷǻ�������ʻԱ���ص�·��ͨ���棬����·ʱ�ߺ���ߣ��������ͣ�̵��С�|e@����ʱ��������ʱ�����绰��|f@�����ٵ�ʱ�򣬳���û��ϵ��|g@��֪��', '3', '4');");
		db.execSQL(basicSql
				+ " values('1', '22', 'Ϊ����ҩ���񫣬�����򾲴���ҩ����ʹҩʱӦע����Щ��', 'a@��ҽ��ָ���½��У�ǧ��Ҫ���з��ã�|b@֢״��ת��Ӧ�𲽼�����|c@���ô���ҩӦ������С������ʧ��֢���ƺ�Ӧֹͣ���ã�|d@�����ֶ�ĳһ��ҩ�����һ������ʱ��Ӧ��ҽ��ָ�����𲽼��ٻ��𲽸�������ҩ�|e@����ҩ��һ�����|f@��֪��', '3', '4');");
		db.execSQL(basicSql
				+ " values('1', '23', '������ֲ����ļ��ݡ���������ô�죿', 'a@���������ű���|b@�ûؼ������ʳ��|c@�����������ŵ�Ҫ��������|d@���ܣ�����û����|e@ι��������|f@�ӵ�����ȥ|g@�յ�|h@����|i@��֪��', '3', '4');");
		db.execSQL(basicSql
				+ " values('1', '24', '�������˺�����������ͣ�����������������ȡ��Щ��ʩ��', 'a@�˹�����|b@�������ఴѹ|c@����Ч������|d@������|e@����ҡ�λ��ߣ�ʹ������|f@��֪��', '3', '4');");
		db.execSQL(basicSql
				+ " values('1', '25', '����Ϊ����������Щ����п����ǰ�֢�ı����źţ�', 'a@�쳣�׿�|b@�쳣��Ѫ|c@ԭ�����ķ��ȡ����������������ؼ���|d@����ԭ��ķ���|e@��˯|f@��֪��', '3', '4');");
		db.execSQL(basicSql
				+ " values('1', '26', '����ΪˮԴ�ܵ���Ⱦ������������Щ������', 'a@����|b@ˮٶ��|c@�׸�|d@�Ҹ�|e@Ѫ���没|f@��֪��', '3', '4');");
		db.execSQL(basicSql
				+ " values('1', '27', 'Ԥ����ͯ��ˮӦ��ע����Щ��', 'a@��ͯ���겻Ҫ������ˮ��Ӿ|b@�������������ˮ����Ӿ|c@��Ӿʱ��Ҫ���֣���Ҫ����Ȼˮ����ϰ��ˮ|d@��ˮǰҪ����׼���|e@�ڶ�������ͯӦ�����ڱ������ߡ��������ڱ������ﳵ|f@��֪��', '3', '4');");
		db.execSQL(basicSql
				+ " values('1', '28', '�ҹ�ũ���ƹ�ʹ�õ��޺�����������������������ʽ��', 'a@���񻯷��ʽ����|b@˫��ʽ����|c@˫�ӽ���ʽ����|d@���׻����|e@������ʽ����|f@���ּ�ʽ��̬����|g@��֪��', '3', '4');");
		

		//�������ʽ����Ϊ����
		db.execSQL(basicSql
				+ " values('1', '1', '��һ��ʲôʱ��ˢ����', '1@�糿ˢ|2@����ˢ|3@������ˢ|4@����ˢ', '4', '3');");
		db.execSQL(basicSql
				+ " values('1', '2', '���Ƿ�������ÿ�����һ�ν������?', '1@��|2@����', '4', '3');");
		db.execSQL(basicSql
				+ " values('1', '3', '������Ҫ����ҽ�ƾ���ʱ��Ӧ����', '1@120|2@119|3@122|4@��֪��', '4', '3');");
		db.execSQL(basicSql
				+ " values('1', '4', '����Ů��һ�����þƵľƾ������ܳ�����', '1@25��|2@50��|3@100��|4@15��|5@�Լ��ľ���|6@��֪��', '4', '3');");
		db.execSQL(basicSql
				+ " values('1', '5', 'Ϊ��Ԥ����Ѫѹ��һ�������ÿ����β��ܳ������ٿˣ�', '1@2�� |2@6��|3@9��|4@12��|5@��֪��', '4', '3');");
		db.execSQL(basicSql
				+ " values('1', '6', 'Ϊ��ȥ������ũҩ����Ⱦ��߲�ˮ����ϴ������Ҫ����ˮ���ݼ����ӣ�', '1@10����|2@15����|3@5����|4@��֪��', '4', '3');");
		db.execSQL(basicSql
				+ " values('1', '7', '��������ʱ��Ӧ�ã�����ѡ�⣩', 'a@��ʪë����ס�ڱ�|b@���������|c@��������119|d@�������ݾ�������|e@�����·������Ϲ�����Ʒ����������|f@��֪��', '4', '4');");
		db.execSQL(basicSql
				+ " values('1', '8', '���ճ�������ע�������Ծ���������������ϰ��Ҫ������ЩҪ�󣿣�5�㣩$$$$$', '', '4', '1');");*/
	}

	private void initQuestionInstance(SQLiteDatabase db) {
		db.execSQL(DBConfigs.CREATE_TABLE_QUESTION_ANSWER);
	}

	// һ����ʵ����Ŀ���ǲ����������ģ���ȷ���������ڸ������ݿ���ṹʱ����Ҫ�����û���������ݿ��е����ݲ��ᶪʧ,�Ӱ汾�����µ��汾����  
	@Override
	public void onUpgrade(SQLiteDatabase db, int arg1, int arg2) {

	}

}