# Onboarding
This is the folder that contains the onboarding UI.
It contains one activity Onboarding.kt and six fragments that are as follows
  - Onboarding_userselfie
  - Onboarding_userdetails
  - Onboarding_medicalreport
  - Onboarding_medicaldetails
  - Onboarding_socioeconomic
  - Onboarding_consent
  
This also contains a OnboardingViewModel that helps in maintaining the state and an ReportItemRecyclerViewAdapter to show the items from the medical report


### Onboarding_consent
###### Layout file: fragment_onboarding_consent.xml
This fragment asks for the user consent as the user starts the journey in the app.

### Onboarding_userselfie
###### Layout file: fragment_onboarding1.xml

The fragment that takes in the selfie image of the person and then processes the image on ActivityResult.
  -  fun processimage(data: Intent?): It is supposed to take in the captured image , run the model and then update the values in onboarding_viewmodel.

### Onboarding_userdetails
###### Layout file: fragment_onboarding2.xml
This fragment shows four fields Age,Gender, BMI and Location.
This sets the fragment number to 2 to save the user state , also on pause the values are put into sharedpreferences so that when the user comes back to the app his values are stored.

onActivityCreated(): Get data from the viewmodel and if it is not zero then set it in the textview else leave the field empty. 

onPause(): Check if any of the textfield is non empty then put the value into the sharedpreferences through the editor 

Viewmodel gets these values from sharedpreferences in its initialisation.

The nextbutton has an onclicklistener that is supposed to enter these into database

### Onboarding_medicalreport
###### Layout file: fragment_onboarding3.xml
The fragment responsible for capturing the image of the medical report and then sends the bitmap to the next fragment for processing and getting the details out of the medical report.

### Onboarding_medicaldetails
###### Layout file: fragment_onboarding4.xml
This fragment makes use of bitmap obtained from the previous fragment and then runs an OCR model(yet to be implemented). And then fills the hashmap. Also , while restoring the user state these values are also obtained from the sharedpreferences. Also onPause we get the value and store them to shared preferences.

The next button must enter them into the db.

### Onboarding_socioeconomic
###### Layout file: fragment_onboarding5.xml

This fragment asks the minimalistic questions that gathers some information required.
When the user clicks the finish button