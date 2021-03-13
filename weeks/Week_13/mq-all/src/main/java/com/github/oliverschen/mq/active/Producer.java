package com.github.oliverschen.mq.active;

import lombok.extern.slf4j.Slf4j;
import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

import static com.github.oliverschen.mq.constaint.Const.*;

/**
 * @author ck
 */
@Slf4j
public class Producer {


    public static void main(String[] args) {
        ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory(BROKER_URL);
        Connection connection = null;
        Session session;
        MessageProducer producer;

        try {
            connection = factory.createConnection();
            // 开启连接
            connection.start();
            //AUTO_ACKNOWLEDGE 自动签收
            session = connection.createSession(true, Session.AUTO_ACKNOWLEDGE);

            // 队列
//            Queue destination = session.createQueue(CK_MSG);
            Topic destination = session.createTopic(CK_MSG_TOPIC);
            producer = session.createProducer(destination);
            for (int i = 0; i < 5; i++) {
                String msg = "my id is : " + i;
                TextMessage textMessage = session.createTextMessage(msg);
                producer.send(textMessage);
                log.info("send success, id is :" + i);
            }
            session.commit();
        }catch (JMSException e) {
            e.printStackTrace();
            log.error("provider send error !");
        }finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (JMSException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
