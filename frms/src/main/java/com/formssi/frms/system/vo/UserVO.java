package com.formssi.frms.system.vo;

import com.formssi.frms.system.domain.SysUser;

/**
 * @author gaoyuzhe
 * @date 2017/12/15.
 */
public class UserVO {
    /**
     * 更新的用户对象
     */
    private SysUser SysUser = new SysUser();
    /**
     * 旧密码
     */
    private String pwdOld;
    /**
     * 新密码
     */
    private String pwdNew;

    public SysUser getSysUser() {
        return SysUser;
    }

    public void setSysUser(SysUser SysUser) {
        this.SysUser = SysUser;
    }

    public String getPwdOld() {
        return pwdOld;
    }

    public void setPwdOld(String pwdOld) {
        this.pwdOld = pwdOld;
    }

    public String getPwdNew() {
        return pwdNew;
    }

    public void setPwdNew(String pwdNew) {
        this.pwdNew = pwdNew;
    }
}
