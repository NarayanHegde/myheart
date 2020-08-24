# FirstUse
This folder contains all the files neccesary for first use for both the user categories.
Content Description is given below:

#### FirstUse Activity
This is the parent activity that holds all the fragments for the first use.

#### FisrtUse_Risks
The task is to show the risks calculated from the information provided in the onboarding. For now, all of it is dummy.
This shows risks based on the user type that is mild risks if primordial and major risks if primary
The Risks can be found in two string arrays with the name **mild_risks** and **major_risks**.

#### FirstUse_Goals
Goal Selection for Primordial users. Goals displayed in a recycler view with **GoalsRecyclerViewAdapter** class.
The selection is stored in the sharedpreferences.
Also, the entry into the notification database is made and notifications are triggered before moving on to the next step. The notifications are stored in a local json file, notifications.json. Any change made in this file will reflect after the new build.

#### FirstUse_PrimaryGoals
Fragment to set goals for the primary users. Based on the risk shown in the previous screen, the user will set goals. The goals will be stored in the tasks table.
Also, the entry into the notification database is made and notifications are triggered before moving on to the next step.The notifications are stored in a local json file, notifications.json. Any change made in this file will reflect after the new build.
