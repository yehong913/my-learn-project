package com.glaway.ids.project.menu.dto;


import com.glaway.foundation.common.dto.FdTeamDto;
import com.glaway.foundation.common.vdata.GLVData;
import com.glaway.foundation.fdk.dev.dto.rep.RepLibraryDto;
import com.glaway.ids.project.projectmanager.dto.Project;


/**
 * A representation of the model object '<em><b>ProjTeamLink</b></em>'.
 * <!-- begin-user-doc -->
 * <!-- end-user-doc -->
 * 
 * @generated
 */
public class ProjTeamLinkDto extends GLVData {

    /**
     * <!-- begin-user-doc -->知识库ID
     * <!-- end-user-doc -->
     * 
     * @generated
     */
    private String libId = null;


    private RepLibraryDto repLibrary;

    /**
     * <!-- begin-user-doc -->项目ID
     * <!-- end-user-doc -->
     * 
     * @generated
     */
    private String projectId = null;

    private Project project;


    private FdTeamDto team;

    /**
     * <!-- begin-user-doc -->团队ID
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
     * Sets the '{@link ProjTeamLinkDto#getLibId() <em>libId</em>}' feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @param newLibId
     *            the new value of the '{@link ProjTeamLinkDto#getLibId() libId}' feature.
     * @generated
     */
    public void setLibId(String newLibId) {
        libId = newLibId;
    }

    /**
     * Returns the value of '<em><b>projectId</b></em>' feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @return the value of '<em><b>projectId</b></em>' feature
     * @generated
     */
    public String getProjectId() {
        return projectId;
    }

    /**
     * Sets the '{@link ProjTeamLinkDto#getProjectId() <em>projectId</em>}' feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @param newProjectId
     *            the new value of the '{@link ProjTeamLinkDto#getProjectId() projectId}' feature.
     * @generated
     */
    public void setProjectId(String newProjectId) {
        projectId = newProjectId;
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
     * Sets the '{@link ProjTeamLinkDto#getTeamId() <em>teamId</em>}' feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @param newTeamId
     *            the new value of the '{@link ProjTeamLinkDto#getTeamId() teamId}' feature.
     * @generated
     */
    public void setTeamId(String newTeamId) {
        teamId = newTeamId;
    }

    public RepLibraryDto getRepLibrary() {
        return repLibrary;
    }

    public void setRepLibrary(RepLibraryDto repLibrary) {
        this.repLibrary = repLibrary;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public FdTeamDto getTeam() {
        return team;
    }

    public void setTeam(FdTeamDto team) {
        this.team = team;
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
        return "ProjTeamLink " + " [libId: " + getLibId() + "]" + " [projectId: " + getProjectId()
               + "]" + " [teamId: " + getTeamId() + "]";
    }
}
