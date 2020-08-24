![Logo](https://github.com/Google-Research-India-Health/CVDapp/blob/master/app/src/main/res/mipmap-hdpi/logo.png)
# MyHeart Android App

This app aims to prevent or delay the onset of a heart disease to the user. It collects basic information from the user and then identifies the health risks to the user, based on these risks the user is asked to set goals.

## Unique Features

* **Minimalistic Onboarding Process**:
The onboarding process is made such that the least possible input is required from the user at the time of onboarding. Also, this can be skipped by the user as none of the fields asked at the time of onboarding are mandatory.

* **Intelligent Health Assistant**: As notifications are not that effective to impart knowledge to the user, our application uses a conversational agent to communicate with the user. It acts as a coach to user guiding them through the challenges and imparting knowledge. As the user interacts with the agent, the application gets to know more about the user helping us in giving a personalised experience to the user

* **Intrinsic and Extrinsic Incentives**: Identifying if the user is intrinsicly motivated or extrinsicly motivated and then providing respective incentives to keep the user involved.

* **Personalisation Index**: An index to show how well the app is personalised for the user. The more the user interacts with the app the more the index increases motivating the user to interact more with the app.

## App Woking

* First Screen is the Login Screen , that uses Google Sign in methods and asks you to sign through a google id.
* The next step is onboarding, there are 6 screens, and each of which asks something about the user. After finish is pressed on the last screen, the app is closed and a background work starts on calculating the user type . As soon as the work is completed , a notification is pushed.
* Then the user lands on the first use page, where the risks to the user are shown and the user is asked to set their goals. At the end of this the notifications are pushed into the notification database.
* Now , comes the home screen. MyTasks page shows the tasks to the user, a task can be selected and marked complete. A completed task is shown green and adds to the points into the myheart page. Also, each task disappears after 3 hours and new tasks are added as soon as the coresponding notification is delivered. The next page is myHeart that shows the points and vitals. The last tab is the assistant tab , where you can communicate with the conversational agent.

## Further Additions
* Gamification in the app
* Proper point system for the app
* Moving the notification file online that enables us to change the notifications without the need of update.
* Sharing the achievements through whatsapp.
* Designing a platform for studies on Behaviour Change Techniques
* Integrate it with other activities tracking app to track user movement better.
