package ruangong.root.utils;

import ruangong.root.bean.UserData;

import javax.servlet.http.HttpSession;

/**
 * @author tao
 */
public class HttpSessionUtil {
    public static void quickSetAttribute(HttpSession session, UserData userData){
        session.setAttribute("email",userData.getEmail());
        session.setAttribute("id",userData.getId());
        session.setAttribute("company",userData.getCompany());
        session.setAttribute("cid",userData.getCid());
        session.setAttribute("rid",userData.getRid());
        session.setAttribute("did",userData.getDid());
        session.setAttribute("role",userData.getRole());
        session.setAttribute("department",userData.getDepartment());
        session.setAttribute("typeId", userData.getTypeId());
    }

    public static void quickRemoveAttribute(HttpSession session){
        session.removeAttribute("email");
        session.removeAttribute("id");
        session.removeAttribute("company");
        session.removeAttribute("cid");
        session.removeAttribute("rid");
        session.removeAttribute("did");
        session.removeAttribute("role");
        session.removeAttribute("department");
    }

    public static void quickUpdateAttribute(HttpSession session,UserData userData){
        HttpSessionUtil.quickRemoveAttribute(session);
        HttpSessionUtil.quickSetAttribute(session,userData);
    }
}
