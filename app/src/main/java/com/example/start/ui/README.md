# UI
This folder contains all the UI related things. This contains many subfolders, the names of which are self explainatory and you can find a readme for each folder. Here is a little description about the files

### Assistant
This is the third tab which is displayed on the home screen. This is to start a conversation with the conversational agent. The list is updated after the user enters the text and receives a response from the agent and the adapter is notified. A call to firebase function is made with the user text as query to get the response and then the response is handled.
**Further Additions**: Resolving the Unauthenticated error being received while calling the function.

### Chatbot Adapter
A recyclerview adapter for displaying the conversation with Conversational Agent. Used in the Assistant Fragment.

### LoginScreen
Firebase Authentication is used to enable Google Sign In. The code can be understood by following the documentation from Firebase. This is also the launcher activity and based on the conditions it navigates to other activities.

### MainActivity
The home screen of the app, responsible for viewing the tab layout using ViewPager.

### MainViewModel
This is the viewmodel that makes connection with the database and has some functions that are used in the app to make changes to the database.

### MyHeart
This is the second fragment that is displayed in the home screen i.e Main Activity. Set value to vitals obtained from the database.
**Further Additions**: Add the function to share achievements with whatsapp. Some permission issues while sharing the achievements.jpg in drawables.

### SectionsPagerAdapter
FragmentPagerAdapter responsible for setting up the ViewPager to display the tablayout on the home screen.

### ViewPicture
This is the activity that is shown to confirm the selected image from the gallery.
