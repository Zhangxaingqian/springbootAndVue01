package cn.myweb01.money01.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
import java.util.Date;

/*监听器,记录session的开始时间和结束时间,还有在线人数*/
public class UserSessionListener implements HttpSessionListener {
    private final static Logger log= LoggerFactory.getLogger(UserSessionListener.class);
    public int count=0;
    @Override
    public void sessionCreated(HttpSessionEvent httpSessionEvent) {
        count++;
        httpSessionEvent.getSession().setAttribute("Count", count);
        log.info("session的id:"+httpSessionEvent.getSession().getId()+
                "创建时间"+new Date(httpSessionEvent.getSession().getCreationTime()));
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent httpSessionEvent) {
        count--;
        httpSessionEvent.getSession().setAttribute("Count", count);
        log.info("当前的在线人数:"+count);
        log.info("session的id:"+httpSessionEvent.getSession().getId()+
                "销毁时间"+new Date(httpSessionEvent.getSession().getLastAccessedTime()));
    }

}
