

    ת�������������
    http://blog.csdn.net/forezp/article/details/71023579
    ���ĳ��Է�־��Ĳ���

���������£�apidoc�ǻ���ע���������ĵ��ģ����������κο�ܣ�����֧�ִ����������ԣ�Ϊ��springbootϵ�е������ԣ����Ա��˸��⡣
һ��apidoc���

apidocͨ����������ע��������api�ĵ��ġ����Դ���û�������ԣ�ֻ��Ҫ��д����ص�ע�ͼ��ɣ���������ͨ��д�򵥵����þͿ������ɸ���ֵ��api�ӿ�ҳ�档������Node.js����������Ҫ��װnode.js������node.js��װ������������Ͳ����ܡ�
����׼������

��װ��node.js��װapi.doc,������ĿԴ�룺https://github.com/apidoc/apidoc ��

ͨ�����װ��

    npm install apidoc -g

����ע����ôд

    @api

@api {method} path [title]

method�����󷽷���
path������·�� 
title(��ѡ)������
@apiDescription

@apiDescription text
text˵��

@apiError

@apiError [(group)] [{type}] field [description]

��group������ѡ������������������Ʒ��飬�����õĻ���Ĭ����Error 4xx 
{type}����ѡ��������ֵ���ͣ����磺{Boolean}, {Number}, {String}, {Object}, {String[]} 
field������ֵ�ֶ����� 
descriptionoptional����ѡ��������ֵ�ֶ�˵��

@apiGroup

@apiGroup name
name�������ƣ�Ҳ�ǵ����ı���

  

����ע�ͣ��μ��ٷ��ĵ���http://apidocjs.com/#params
�ġ�д������
����д�����ļ�

����Ŀ����Ŀ¼�½�һ��apidoc.json�ļ���

{
  "name": "example",
  "version": "0.1.0",
  "description": "A basic apiDoc example"
}



�������òο���http://apidocjs.com/#configuration
д��ע��:


    /**
     * @api {POST} /register ע���û�
     * @apiGroup Users
     * @apiVersion 0.0.1
     * @apiDescription ����ע���û�
     * @apiParam {String} account �û��˻���
     * @apiParam {String} password ����
     * @apiParam {String} mobile �ֻ���
     * @apiParam {int} vip = 0  �Ƿ�ע��Vip��� 0 ��ͨ�û� 1 Vip�û�
     * @apiParam {String} [recommend] ������
     * @apiParamExample {json} ����������
     *                ?account=sodlinken&password=11223344&mobile=13739554137&vip=0&recommend=
     * @apiSuccess (200) {String} msg ��Ϣ
     * @apiSuccess (200) {int} code 0 �����޴��� 1�����д���
     * @apiSuccessExample {json} ��������:
     *                {"code":"0","msg":"ע��ɹ�"}
     */


��apidoc���������ĵ�����

��cd�����̵����Ŀ¼���������Ŀ��������ĵ���Ŀ¼���ҽ�����docapi��

�����

    apidoc -i chapter4/ -o apidoc/

-i ����Ŀ¼ -o ���Ŀ¼

chapter4���ҵĹ�������

���Կ�����apidocĿ¼�����˺ܶ��ļ�:

Paste_Image.png

��index.html,���Կ����ĵ�ҳ��:

QQͼƬ20170417175251.png
�塢�ο�����
ʹ��apidoc ����Restful web Api�ĵ�
http://blog.csdn.net/soslinken/article/details/50468896