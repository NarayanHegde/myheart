# WorkManager
This folder comprises of all the background works that are done in the app using WorkManager

### NotificationWork
This is the work to display the notification to the user, after the notification is displayed, a task is added as a followup and displayed in the mytask page. Also, a work to remove the task is scheduled with a delay of 3 hours.

### ScheduleNotificationWork
This gets all the notifications from the notifications database and then schedules them at the appropriate time for the day , also checks the frequency of the notification. The notifications are entered from the notifications.json file in res/raw folder.
Also it schedules itself for the next day at midnight.

### TaskRefresh
This updates the points if the task is completed. In any case removes the task from the task database.

### UserTypeWork
This is just run after the onboarding is completed. This works on the data provided by the user and then determines the type and the risks the user has into the sharedpreferences. Also, sends a notification that the app is ready to proceed.
