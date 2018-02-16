package com.qa.data;

public class CreateProject {
	
		boolean userHasFreePlan;
		boolean userHasProfessionalPlan;
		boolean userHasEnterprisePlan;
		boolean userHasAcademicPlan;
		String projectName;					 
		String measurementSystem; 			 
		boolean isPrivate;					
		String description;					
		
		public CreateProject(boolean userHasFreePlan,
		boolean userHasProfessionalPlan,
		boolean userHasEnterprisePlan,
		boolean userHasAcademicPlan,
		String projectName,	
		String measurementSystem,
		boolean isPrivate,
		String description){
			
			this.description = description;
			this.isPrivate = isPrivate;
			this.measurementSystem = measurementSystem;
			this.projectName = projectName;
			this.userHasAcademicPlan = userHasAcademicPlan;
			this.userHasEnterprisePlan = userHasEnterprisePlan;
			this.userHasFreePlan = userHasFreePlan;
			this.userHasProfessionalPlan = userHasProfessionalPlan;
			
		}

		public boolean isUserHasFreePlan() {
			return userHasFreePlan;
		}

		public void setUserHasFreePlan(boolean userHasFreePlan) {
			this.userHasFreePlan = userHasFreePlan;
		}

		public boolean isUserHasProfessionalPlan() {
			return userHasProfessionalPlan;
		}

		public void setUserHasProfessionalPlan(boolean userHasProfessionalPlan) {
			this.userHasProfessionalPlan = userHasProfessionalPlan;
		}

		public boolean isUserHasEnterprisePlan() {
			return userHasEnterprisePlan;
		}

		public void setUserHasEnterprisePlan(boolean userHasEnterprisePlan) {
			this.userHasEnterprisePlan = userHasEnterprisePlan;
		}

		public boolean isUserHasAcademicPlan() {
			return userHasAcademicPlan;
		}

		public void setUserHasAcademicPlan(boolean userHasAcademicPlan) {
			this.userHasAcademicPlan = userHasAcademicPlan;
		}

		public String getProjectName() {
			return projectName;
		}

		public void setProjectName(String projectName) {
			this.projectName = projectName;
		}

		public String getMeasurementSystem() {
			return measurementSystem;
		}

		public void setMeasurementSystem(String measurementSystem) {
			this.measurementSystem = measurementSystem;
		}

		public boolean isPrivate() {
			return isPrivate;
		}

		public void setPrivate(boolean isPrivate) {
			this.isPrivate = isPrivate;
		}

		public String getDescription() {
			return description;
		}

		public void setDescription(String description) {
			this.description = description;
		}
		
		
}