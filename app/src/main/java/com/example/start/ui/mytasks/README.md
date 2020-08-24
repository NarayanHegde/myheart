# mytasks
This is the folder that contains the Tasks UI.
It contains a fragment, **MyTasks** and a RecyclerViewAdapter **TasksAdapter**

#### MyTasks
A simple fragment with a recycler view to show all the tasks.
Also it handles special click functions like uploading an image and then entering into the database.

#### TasksAdapter
An adapter that attaches the onClickListener on the basis of the Entrytype of the entry.
For now, every entry other than upload the photo is considered as boolean.
**Further Addition**: Making onClickListener for all types of tasks such that they can also take some input from the user.
