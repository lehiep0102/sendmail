package sendmail;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.BodyPart;
import javax.mail.Multipart;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMultipart;
import java.time.format.DateTimeFormatter;  
import java.time.LocalDateTime;  

class Employee {

    private String emailTo;
    private String emailSubject;
    private String emailBody;
    private String emailAttachments;
    private String emailnoncryption;
    private String emailencryption;
    private String acnt_no;
    private String birth_dt;

    public String getEmailTo() {
        return emailTo;
    }

    public void setEmailTo(String emailTo) {
        this.emailTo = emailTo;
    }

    public String getEmailSubject() {
        return emailSubject;
    }

    public void setEmailSubject(String emailSubject) {
        this.emailSubject = emailSubject;
    }

    public String getEmailBody() {
        return emailBody;
    }

    public void setEmailBody(String emailBody) {
        this.emailBody = emailBody;
    }

    public String getEmailAttachments() {
        return emailAttachments;
    }

    public void setEmailAttachments(String emailAttachments) {
        this.emailAttachments = emailAttachments;
    }

    public String getEmailnoncryption() {
        return emailnoncryption;
    }

    public void setEmailnoncryption(String emailnoncryption) {
        this.emailnoncryption = emailnoncryption;
    }

    public String getEmailencryption() {
        return emailencryption;
    }

    public void setEmailencryption(String emailencryption) {
        this.emailencryption = emailencryption;
    }

    public String getAcnt_no() {
        return acnt_no;
    }

    public void setAcnt_no(String acnt_no) {
        this.acnt_no = acnt_no;
    }

    public String getBirth_dt() {
        return birth_dt;
    }

    public void setBirth_dt(String birth_dt) {
        this.birth_dt = birth_dt;
    }

    public Employee() {
        // TODO Auto-generated constructor stub
    }

    public Employee(String emailTo, String emailSubject, String emailBody,
            String emailAttachments, String emailnoncryption, String emailencryption, String acnt_no,String birth_dt) {
        super();
        this.emailTo = emailTo;
        this.emailSubject = emailSubject;
        this.emailBody = emailBody;
        this.emailAttachments = emailAttachments;
        this.emailencryption = emailencryption;
        this.emailnoncryption = emailnoncryption;
        this.acnt_no = acnt_no;
        this.birth_dt = birth_dt;
    } 
}

class EmployeeDao {

    private Connection con;

    private static final String GET_EMPLOYEES = "select t.emailto, t.emailsubject,t.emailbody,t.emailattachments,t.emailnoncryption,g.emailencryption, t.acnt_no, fud_ddmmyyyy(e.birth_dt) birth_dt from FUDEMAIL t ,fud_account e,FUDNONTEMP g where t.acnt_no = e.acnt_no and t.SEND_YN = 'N' order by acnt_no asc";
    private void connect() throws InstantiationException,
            IllegalAccessException, ClassNotFoundException, SQLException {
        Class.forName("oracle.jdbc.driver.OracleDriver")
                .newInstance();
        con = DriverManager
                 .getConnection("jdbc:oracle:thin:quote/123456@10.0.25.24:1521/oracledb");
                //.getConnection("jdbc:oracle:thin:@10.0.25:1521:oracledb", "quote","123456");
        if (con != null) {
            System.out.println("Connected to the database!");
        } else {
            System.out.println("Failed to make connection!");
        }
    }

    public List<Employee> getEmployees() throws Exception {
        connect();
        PreparedStatement ps = con.prepareStatement(GET_EMPLOYEES);
        ResultSet rs = ps.executeQuery();
        //System.out.println(rs);
        List<Employee> result = new ArrayList<>();
        while (rs.next()) {
            result.add(new Employee(
                    rs.getString("emailTo"),
                    rs.getString("emailSubject"),
                    rs.getString("emailBody"),
                    rs.getString("emailattachments"),
                    rs.getString("emailnoncryption"),
                    rs.getString("emailencryption"),
                    rs.getString("acnt_no"),
                    rs.getString("birth_dt")));
        }
        disconnect();
        return result;
    }

    private void disconnect() throws SQLException {
        if (con != null) {
            //System.out.println("Failed to select table!");
            con.close();
        }
    }

}

class senderto {
    private String acnt_no;
    private String mailTo;
    private String mailSubject;
    private String emailbody;
    private String mailAttachments;
    private String EMAILENCRYPTION;

    public String getEmailbody() {
        return emailbody;
    }

    public void setEmailbody(String emailbody) {
        this.emailbody = emailbody;
    }

    
    
    public String getEMAILENCRYPTION() {
        return EMAILENCRYPTION;
    }

    public void setEMAILENCRYPTION(String EMAILENCRYPTION) {
        this.EMAILENCRYPTION = EMAILENCRYPTION;
    }

     public String getAcnt_no() {
        return acnt_no;
    }

    public void setAcnt_no(String acnt_no) {
        this.acnt_no = acnt_no;
    }
    
    public String getMailTo() {
        return mailTo;
    }

    public void setMailTo(String mailTo) {
        this.mailTo = mailTo;
    }

    public String getMailSubject() {
        return mailSubject;
    }

    public void setMailSubject(String mailSubject) {
        this.mailSubject = mailSubject;
    }

    public String getMailAttachments() {
        return mailAttachments;
    }

    public void setMailAttachments(String mailAttachments) {
        this.mailAttachments = mailAttachments;
    }
   
    public senderto() {
    };
   
    public senderto(String acnt_no, String mailTo, String mailSubject, String emailbody, String mailAttachments, String EMAILENCRYPTION){
        super();
        this.acnt_no = acnt_no;
        this.mailTo = mailTo;
        this.mailSubject = mailSubject;
        this.emailbody = emailbody;
        this.mailAttachments = mailAttachments;
        this.EMAILENCRYPTION = EMAILENCRYPTION;
        
    }
    
}

 class Encrypto{
    public void enCrypt(Employee employees) throws MessagingException, IOException, Exception {
        
        if (employees == null) {
            return;
        }
        String passphrase = "p@ss123456";
        if(employees.getBirth_dt()!= null || employees.getBirth_dt() != "")
        {
            passphrase = employees.getBirth_dt();
        }
        AESHelper AES = new AESHelper();
        
        String originalString = employees.getEmailnoncryption();
        
        String encryptedString =AES.encrypt(passphrase, originalString);
        System.out.println(passphrase);
       
         
        String encryptedStringJSON = "{\"data\":\"" + encryptedString+ "\"}";

        String encryptedDocument = employees.getEmailencryption().replace("/*{{ENCRYPTED_PAYLOAD}}*/\"\"", encryptedStringJSON);
      
        
        String jdbcUrl = "jdbc:oracle:thin:quote/123456@10.0.25.24:1521/oracledb";
        String sql = "update FUDEMAIL set ENCRY_YN = 'Y',EMAILATTACHMENTS = ? where EMAILTO = ? and acnt_no = ? ";

    try (Connection conn = DriverManager.getConnection(jdbcUrl);
                PreparedStatement stmt = conn.prepareStatement(sql);) {
            stmt.setString(1, encryptedDocument);
            stmt.setString(2, employees.getEmailTo());
            stmt.setString(3, employees.getAcnt_no());
            stmt.executeUpdate();

            System.out.println("Database updated successfully ");
        } catch (SQLException e) {
            System.out.println("Database updated false ");
        }
    }
   public void enCrypt(List<Employee> employees) throws MessagingException, IOException, Exception {
        for (Employee employee : employees) {
            enCrypt(employee);
        }
    }
}

class MailDao {

    private Connection con;

    private static final String GET_EMPLOYEES = "select acnt_no,emailto, emailsubject,emailbody,emailattachments,EMAILENCRYPTION from FUDEMAIL where ENCRY_YN = 'Y' and SEND_YN = 'N'";

    private void connectr() throws InstantiationException,
            IllegalAccessException, ClassNotFoundException, SQLException {
        Class.forName("oracle.jdbc.driver.OracleDriver")
                .newInstance();
        con = DriverManager.getConnection("jdbc:oracle:thin:quote/123456@10.0.25.24:1521/oracledb");
                //.getConnection("jdbc:oracle:thin:@10.0.31.100:1521:oracledb", "quote","123456");
        if (con != null) {
            System.out.println("Connected to the database!");
        } else {
            System.out.println("Failed to make connection!");
        }
    }

    public List<senderto> getsendto() throws Exception {
        connectr();
        PreparedStatement ps = con.prepareStatement(GET_EMPLOYEES);
        ResultSet rs = ps.executeQuery();
        //System.out.println(rs);
        List<senderto> result = new ArrayList<>();
        while (rs.next()) {
            result.add(new senderto(
                    rs.getNString("acnt_no"),
                    rs.getString("emailTo"),
                    rs.getString("emailSubject"),
                    rs.getString("emailbody"),
                    rs.getString("emailattachments"),
                    rs.getString("EMAILENCRYPTION")));
        }
        disconnectr();
        return result;
    }

    private void disconnectr() throws SQLException {
        if (con != null) {
            //System.out.println("Failed to select table!");
            con.close();
        }
    }

}

class EmailSender {

    private Session session;
    private Object item;

    private void init() {
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "");
        props.put("mail.smtp.port", "");

        session = Session.getInstance(props, new javax.mail.Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("", "");
            }
        });
    }

    @SuppressWarnings("empty-statement")
    public void sendEmail(senderto s) throws MessagingException, IOException {
        init();
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyyMM");  
        LocalDateTime now = LocalDateTime.now();
        Message message = new MimeMessage(session);
        message.addHeader("Content-type", "text/HTML; charset=utf-8");
        message.setFrom(new InternetAddress(""));
        message.setRecipients(Message.RecipientType.TO,
                InternetAddress.parse(s.getMailTo()));
        message.setSubject(s.getMailSubject());
        BodyPart messageBodyPart = new MimeBodyPart();
        Multipart multipart = new MimeMultipart();
        messageBodyPart.setContent(s.getEmailbody(), "text/html; charset=utf-8");
        multipart.addBodyPart(messageBodyPart);
        messageBodyPart = new MimeBodyPart();
        String filename;
        String path = "D:\\emailfund\\" ;
        filename = s.getAcnt_no()+".html";
         
        File dir = new File(path+filename);
            dir.deleteOnExit();
        
        try {
            String content = s.getMailAttachments();
            String filewrite = path + filename;
            File file = new File(filewrite);

            // If file doesn't exists, then create it
            if (!file.exists()) {
                file.createNewFile();
            }

            FileWriter fw = new FileWriter(file.getAbsoluteFile());
            try (BufferedWriter bw = new BufferedWriter(fw)) {
                bw.write(content);
            }
        } catch (Exception e) {
            System.out.println(e);
        }

        DataSource source = new FileDataSource(path+filename);
        messageBodyPart.setDataHandler(new DataHandler(source));
        messageBodyPart.setFileName(filename);
        multipart.addBodyPart(messageBodyPart);
        message.setContent(multipart);
        System.out.println(filename);
        Transport.send(message);
       
        String jdbcUrl = "jdbc:oracle:thin:quote/123456@10.0.25.24:1521/oracledb";
        String sql = "update FUDEMAIL set SEND_YN = 'Y' where acnt_no = ? and EMAILTO = ? ";

    try (Connection conn = DriverManager.getConnection(jdbcUrl);
                PreparedStatement stmt = conn.prepareStatement(sql);) {
            stmt.setString(1,s.getAcnt_no());
            stmt.setString(2,s.getMailTo());
            stmt.executeUpdate();
            
            System.out.println("Email Sent Successfully !!");
        } catch (SQLException e) {
            System.out.println("Email Sent false (--)");
        }
        //System.out.println("Email Sent Successfully !!");
    }

    public void sendEmail(List<senderto> sendertos) throws MessagingException, IOException {
        for (senderto Senderto : sendertos) {
            sendEmail(Senderto);
        }
    }

	public Object getItem() {
		return item;
	}

	public void setItem(Object item) {
		this.item = item;
	}
}

public class main {

    /**
     *
     * @param args
     * @throws Exception
     */
    public  static void main(String[] args) throws Exception {
        //login lg = new login();
        //lg.show();
       EmployeeDao dao = new EmployeeDao();
       List<Employee> list = dao.getEmployees();
       Encrypto encryp = new Encrypto();
       encryp.enCrypt(list);
       MailDao send = new MailDao();
       List<senderto> sendsr = send.getsendto();
       EmailSender sender = new EmailSender();
       sender.sendEmail(sendsr);
    }
}
