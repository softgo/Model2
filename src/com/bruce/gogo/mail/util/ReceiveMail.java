package com.bruce.gogo.mail.util;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;


import javax.mail.Address;
import javax.mail.BodyPart;
import javax.mail.Flags;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.Part;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.URLName;
import javax.mail.internet.MimeUtility;
public class ReceiveMail implements Serializable {

    private final static String SSL_FACTORY = "javax.net.ssl.SSLSocketFactory";
    //用户账号
    private String account;
    //密码
    private String password;
    //POP3邮件服务器
    private String pop3Host;
    //POP3端口
    private Integer pop3Port;
    //accunt属性setter 和getter方法
    private Store store;

    public ReceiveMail() {
    }

    public ReceiveMail(String account, String password, String pop3Host, Integer pop3Port) {
        this.account = account;
        this.password = password;
        this.pop3Host = pop3Host;
        this.pop3Port = pop3Port;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPop3Host() {
        return pop3Host;
    }

    public void setPop3Host(String pop3Host) {
        this.pop3Host = pop3Host;
    }

    public Integer getPop3Port() {
        return pop3Port;
    }

    public void setPop3Port(Integer pop3Port) {
        this.pop3Port = pop3Port;
    }

    public Store getStore() {
        if (this.store == null || !this.store.isConnected()) {
            try {
                Properties props = new Properties();
                if (isGamil()) {
//                    props.setProperty("mail.pop3.socketFactory.class", SSL_FACTORY);
                	props.setProperty("mail.smtp.starttls.enable", "true");
                    //创建mail的session
                    Session session = Session.getDefaultInstance(props);
                    //使用pop3协议接收邮件
                    URLName url = new URLName("pop3", getPop3Host(), getPop3Port(), null, getAccount(), getPassword());
                    //得到邮箱的储存对象
                    Store store = session.getStore(url);
                    store.connect();
                    this.store = store;
                }
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("连接邮箱异常，请检查连接信息");
            }
        }
        return this.store;
    }

    public void setStore(Store store) {
        this.store = store;
    }

    //对于Gmail邮箱需要特别处理
    private boolean isGamil() {
        if (this.account == null || this.account.trim().equals("")) {
            return false;
        }
        if (this.account.lastIndexOf("@gmail.com") != -1) {
            return true;
        }
        return false;
    }

    //获取邮件
    public void getMessage() {
        try {
            Folder inbox = getStore().getFolder("INBOX");
            inbox.open(Folder.READ_WRITE);
            //得到INBOX中的所有的信息
            Message[] messages = inbox.getMessages();
            for (int i = 0; i < messages.length; i++) {
                System.out.println("-----第" + i + 1 + "封邮件消息");
                printMessage(messages[i]);
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("获取邮件失败");
        }
    }

    //得到接收的日期，优先返回发送日期，其次返回收信日期
    public Date getReceivedDate(Message m) throws Exception {
        if (m.getSentDate() != null) {
            return m.getReceivedDate();
        }
        if (m.getReceivedDate() != null) {
            return m.getReceivedDate();
        }
        return new Date();
    }

    //得到抄送人的地址
    public List<String> getCopyAddress(Message m) throws Exception {
        Address[] addresses = m.getRecipients(Message.RecipientType.CC);
        return getAddresses(addresses);
    }

    //得到文件的后缀
    private String getFileSuffix(String fileName) {
        if (fileName == null || fileName.trim().equals("")) {
            return "";
        }
        int dotPos = fileName.lastIndexOf(".");
        if (dotPos != -1) {
            return fileName.substring(dotPos);
        }
        return "";
    }

   /* //获得邮件的附件
    public List<FileObject> getFiles(Message m) throws Exception {
        List<FileObject> list = new ArrayList<FileObject>();
        //是混合类型的数据就处理
        if (m.isMimeType("mutipart/mixed")) {
            Multipart mp = (Multipart) m.getContent();
            //得到邮件的Mutipart对象并得到内容中part的数量
            int count = mp.getCount();
            for (int i = 1; i < count; i++) {
                //获取附件
                Part part = mp.getBodyPart(i);
                //获取邮件的附件名
                String serverFileName = MimeUtility.decodeText(part.getFileName());
                //生成UUID作为本系统中唯一的文件标识
                String fileName = UUID.randomUUID().toString();
                File file = new File(fileName + getFileSuffix(serverFileName));
                //读写文件
                FileOutputStream fos = new FileOutputStream(file);
                InputStream is = part.getInputStream();
                BufferedOutputStream outs = new BufferedOutputStream(fos);
                //使用IO流读取邮件附件
                byte[] b = new byte[1024];
                int hasRead = 0;
                while ((hasRead = is.read(b)) > 0) {
                    outs.write(b, 0, hasRead);
                }
                outs.close();
                is.close();
                fos.close();
                //封装对象  ***************************************************************
                FileObject fileObject = new FileObject(serverFileName, file);
                list.add(fileObject);
            }
        }
        return list;
    }
      */
    //处理邮件正文的工具方法
    private StringBuffer getContent(Part part, StringBuffer result) throws Exception {
        if (part.isMimeType("multipart/*")) {
            Multipart p = (Multipart) part.getContent();
            int count = p.getCount();
            // Multipart的第一部分是text/plain
            // 第二部分是text/html的格式，只解析一部分就好
            if (count > 1) {
                count = 1;
            }
            for (int i = 0; i < count; i++) {
            BodyPart bp=p.getBodyPart(i);
             //此处递归调用
            getContent(bp,result);
            }
        } else if(part.isMimeType("text/*")){
            //遇到text和html直接得到内容
            result.append(part.getContent());
        }
        return result;
    }

    /**
     * 返回邮件正文内容.
     * @param m 邮件内容
     * @return 邮件正文
     * @throws Exception
     */

    public String getContent(Message m) throws Exception{
       StringBuffer sb=new StringBuffer("");
        return getContent(m,sb).toString(); //递归调用
    }
    //判断一封邮件是否已经读过，true读过
    public boolean hasRead(Message m)throws Exception{
        Flags flags=m.getFlags();
        return flags.contains(Flags.Flag.SEEN);
    }
    //获得一封邮件的所有的收件人s
    public List<String> getAllRecipients(Message m) throws Exception{
       Address[] addresses=m.getAllRecipients();
        return getAddresses(addresses);
    }
    //工具方法  将参数的地址字符串封装成集合
    public List<String> getAddresses(Address[] addresses) {
        List<String> list = new ArrayList<String>();
        if (addresses == null) {
            return list;
        }
        for (Address a : addresses) {
            list.add(a.toString());
        }
        return list;
    }
    //得到发件人的地址
    public String getSender(Message m) throws Exception{
        Address[] addresses=m.getFrom();
        return MimeUtility.decodeText(addresses[0].toString());
    }

    //打印Message消息的信息
    private void printMessage(Message m) throws Exception {
        System.out.println(m.toString());
        System.out.println("接受日期：" + getReceivedDate(m));
        System.out.println("抄送地址：" + getCopyAddress(m));
        System.out.println("是否已读：" + (hasRead(m)?"是":"没有读取过"));
        System.out.println("所有收件人：" + getAllRecipients(m));
        System.out.println("发件人地址："+getSender(m));
        System.out.println("邮件正文：" + getContent(m));

    }
    public static void main(String args[])throws Exception{
    }
}
