package com.glaway.ids.project.projecttemplate.dto;


import com.glaway.foundation.common.dto.FdTeamDto;
import com.glaway.foundation.common.vdata.GLVData;
import com.glaway.foundation.fdk.dev.dto.rep.RepLibraryDto;


/**
 * A representation of the model object '<em><b>ProjTmplTeamLink</b></em>'.
 * <!-- begin-user-doc -->
 * <!-- end-user-doc -->
 * 
 * @generated
 */
public class ProjTmplTeamLinkDto extends GLVData {

    /**
     * <!-- begin-user-doc -->知识库ID
     * <!-- end-user-doc -->
     * 
     * @generated
     */
    private String libId = null;

    private RepLibraryDto repLibrary;

    /**
     * <!-- begin-user-doc -->项目模板ＩＤ
     * <!-- end-user-doc -->
     * 
     * @generated
     */
    private String projTemplateId = null;

    private ProjTemplateDto projTemplate;

    private FdTeamDto team;

    /**
     * <!-- begin-user-doc -->团队ＩＤ
     * <!-- end-user-doc -->
     * 
     * @generated
     */
    private String teamId = null;

    /**
     * Returns the value of '<em><b>libId</b></em>' feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @return the value of '<em><b>libId</b></em>' feature
     * @generated
     */
    public String getLibId() {
        return libId;
    }

    /**
     * Sets the '{@link ProjTmplTeamLinkDto#getLibId() <em>libId</em>}' feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @param newLibId
     *            the new value of the '{@link ProjTmplTeamLinkDto#getLibId() libId}' feature.
     * @generated
     */
    public void setLibId(String newLibId) {
        libId = newLibId;
    }

    /**
     * Returns the value of '<em><b>projTemplateId</b></em>' feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @return the value of '<em><b>projTemplateId</b></em>' feature
     * @generated
     */
    public String getProjTemplateId() {
        return projTemplateId;
    }

    /**
     * Sets the '{@link ProjTmplTeamLinkDto#getProjTemplateId() <em>projTemplateId</em>}' feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @param newProjTemplateId
     *            the new value of the '{@link ProjTmplTeamLinkDto#getProjTemplateId() projTemplateId}
     *            ' feature.
     * @generated
     */
    public void setProjTemplateId(String newProjTemplateId) {
        projTemplateId = newProjTemplateId;
    }

    /**
     * Returns the value of '<em><b>teamId</b></em>' feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @return the value of '<em><b>teamId</b></em>' feature
     * @generated
     */
    public String getTeamId() {
        return teamId;
    }

    /**
     * Sets the '{@link ProjTmplTeamLinkDto#getTeamId() <em>teamId</em>}' feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @param newTeamId
     *            the new value of the '{@link ProjTmplTeamLinkDto#getTeamId() teamId}' feature.
     * @generated
     */
    public void setTeamId(String newTeamId) {
        teamId = newTeamId;
    }

    public ProjTemplateDto getProjTemplate() {
        return projTemplate;
    }

    public void setProjTemplate(ProjTemplateDto projTemplate) {
        this.projTemplate = projTemplate;
    }

    public FdTeamDto getTeam() {
        return team;
    }

    public void setTeam(FdTeamDto team) {
        this.team = team;
    }

    public RepLibraryDto getRepLibrary() {
        return repLibrary;
    }

    public void setRepLibrary(RepLibraryDto repLibrary) {
        this.repLibrary = repLibrary;
    }

    /**
     * A toString method which prints the values of all EAttributes of this instance.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    public String toString() {
        return "ProjTmplTeamLink " + " [libId: " + getLibId() + "]" + " [projTemplateId: "
               + getProjTemplateId() + "]" + " [teamId: " + getTeamId() + "]";
    }

}
