Phase 2 Operations Instructions  
  
############# Group 0116 ##################
Author: (The Order is arranged by alphabet)  
  
  
		Chen Hua  
		Haoran Li (Edward)  
		JiangChuan Hu  
		Mincong Wen (Locky)  
		Yuzhan Gao  
		Zijing Xu (Lotus)  
  

Environment Set Up                                               // TBD 

SDK Android 10.0+ (R)
JDK 1.8
API 30
EMULATOR 720p Galaxy Nexus
etc. 

############################################

Instructions: 

Default Set Up : 

RegularUser (For Test) 

\---------------------------\   \---------------------------\
\       RegularUser         \   \       RegularUser         \
\Username: Kelvin			\   \Username: Cissy			\
\Password: kel				\   \Password: cis				\
\IsFrozen : true		    \   \IsFrozen : true		    \
\Non unfreeze request sent	\   \Non unfreeze request sent	\
\---------------------------\   \---------------------------\

\---------------------------\
\       RegularUser         \  
\Username: Gary 			\
\Password: gar				\
\IsFrozen : false		    \
\---------------------------\

AdminUser: 

\---------------------------\
\		AdminUser		    \
\Username: PrimaryAdmin     \
\Password:admin			    \
\---------------------------\


##Functionality 1 : "Users who participate in trades should be able to create an account, log in with a user name and password, 
maintain a list of items that they are willing to lend and a list of items that they wish to borrow. They should also be able to browse the inventory, 
looking for items to add to their wish list."

    Run the Android App --> Click the bottom text "don't have an account yet" --> Enter your username, password and confirm your password to
    create an account. Entering an exist username, Or password and confirm password don't match OR leave the text empty will make you fail to
    create an account. Also, you can click the eye button to show your hidden password. If you want to go back in the halfway, click the left-arrow
    button to go back to the log in menu.

    Then click "SIGN UP" to create an account

    The page will redirect you to the log in menu and then you can log in and see the RegularUser Menu.

    Click the icon button at the top left corner of the RegularUser menu, you can have access to INVENTORY, WISH LIST, WILLING TO LEND

## Functionality 2: "A frozen account is one where you can log in and look for items, but you cannot arrange any transactions. A user who has been frozen can request
that the administrative user unfreezes their account."

    By default, we set up two frozen regular user account and an AdminUser account
    \---------------------------\
    \		AdminUser		    \
    \Username: PrimaryAdmin     \
    \Password:admin			    \
    \---------------------------\

    \---------------------------\   \---------------------------\
    \       RegularUser         \   \       RegularUser         \
    \Username: Kelvin			\   \Username: Cissy			\
    \Password: kel				\   \Password: cis				\
    \IsFrozen : true		    \   \IsFrozen : true		    \
    \Non unfreeze request sent	\   \Non unfreeze request sent	\
    \---------------------------\   \---------------------------\

    Log out to the log in menu and log in as Kelvin, we see there is a screen navigated you to the Unfreeze Request menu. Click no to the regular user menu. We see that
    only the VIEW MARKET("look for items") and INVENTORY button works. Click the icon button on the top left corner OR just choose yes to enter the Unfreeze Request Menu.

    In the regular user menu, click the icon button at the top left corner and then click the info button at the top right corner, then you can see your account status.

    Note that an unfreeze request cannot be empty. Also, if your account status is normal, you are not allowed to send a request. You can enter a text and send it to the
    Admin users.

    Go back to the log in menu and log in as PrimaryAdmin. Click the UNFREEZE USERS and you can see the frozen users and the unfreeze request you just sent. The unfreeze
    button is designed as a snow icon.

## Functionality 3:"If a user wants to advertise that they have an item to trade, they should be able create a new item in the system with a name and description. It
is not actually added to the system until an administrative user looks at it and confirms that it should be added. This is one way to prevent people from selling things
they can't possibly own (example: the North Pole, a dinosaur, etc.) or otherwise entries that are not tangible objects (example: someone's self-respect,
their loyalty, etc.)."

    By default, we have a default regular user called Gary
    \---------------------------\
    \       RegularUser         \
    \Username: Gary 			\
    \Password: gar				\
    \IsFrozen : false		    \
    \---------------------------\

        \---------------------------\
        \		AdminUser		    \
        \Username: PrimaryAdmin     \
        \Password:admin			    \
        \---------------------------\

    Log in to the regular user's menu, then click REQUEST ADDING ITEM, you can add name, description and image to this new item and click
    REQUEST ADDING ITEM to send a request. After, log in as an Admin User and go to the APPROVE NEW ITEMS REQUEST. This admin user can choose if the
    request should be approved.

## Functionality 4:"A user should be able to see the most recent three items that they traded in a one-way or two-way trade and their top three most frequent trading partners."

## Functionality 5:"Administrative users should be able to log in with a username and a password in order to confirm that a user account should be frozen (even though the system
identifies for them which users to freeze), add new items to user's lists of available items, or change any of the threshold values (example: how many more times must you
lend items before you can borrow)."

    By default, we have an Admin User:
    \---------------------------\
    \		AdminUser		    \
    \Username: PrimaryAdmin     \
    \Password:admin			    \
    \---------------------------\

    Log in as a PrimaryAdmin, click on VIEW SUSPICIOUS USER to see who should be frozen. Click on APPROVE ADDING ITEMS to see which item should be added to Regular
    User's inventory. In the AdminUser's menu, click the button at the top right corner, then click CHANGE THRESHOLD and then you can change the threshold values
    in this system.

## Functionality 6:"The initial administrative user should be able to add subsequent administrative users to the system."

    Log in as a PrimaryAdmin. click the button at the top right corner, then click ADDING NEW ADMIN to add a new admin to this system. Please note that
    an existing admin username OR any empty editText OR an unmatched password and confirm password will result in a failure of creating account.

## Functionality 8: "Each transaction can be permanent or temporary. With a permanent transaction you give away an object that you do not expect to get
back or you borrow an object that is now yours (referred to here as "permanently borrowing"). Either way, that item should be deleted from the wish list
 and list of available items of the users involved.A temporary transaction has a time limit. This means that a second exchange is supposed to happen in
 the future."

## Functionality 9: "Once a transaction has been set up by one user on your system, the other user has to agree to the proposed trade. They also have to agree to a time and a place
to meet. These two pieces of information should be entered by one of the users. The other can then confirm or edit. If they edit, then the original user can confirm or edit, and so on,
until they both agree, up to a threshold of 3 edits per user. If they both edit the time/place information three times without confirming, the system should print to the screen that
their transaction has been cancelled. Once the meeting has been set up, the associated transaction is open. To close it, both users have to confirm that the transaction took place,
after the meeting was supposed to take place. For a temporary transaction, the second meeting also has to be confirmed by both users, or else the transaction remains open,
which counts towards both users potentially having their accounts frozen."

## Functionality 10: "Give the admin user the ability to undo every action taken by regular users that can reasonably be undone."

The basic mechanism of undo is that the system will record every modification or any action that the RegularUser class has made. Thus, I separate the cause and effect of undo functionality. 

Cause: The user has made an action
Effect: A piece of history is recorded so later on, this action can be undone.

To separate them, I made the RegularUser to be the Observable, and the RegularUserActionStack to be the Observer. Once an action is detected from the user, the execute method in the user will be called and then the RegularUserActionStack will call the update method. Thereafter, a piece of history is saved.


## Functionality 11: "Have your program automatically suggest items to lend to a given user, if there is something that is both on one person's wish list and the other's lending list.
In other words, when the user sees something they want to borrow, they can ask the program for a suggestion of what they could lend this user in return."

## Functionality 12: "Create a new type of account that allows a user to look at the various parts of the program without being able to trade or communicate with the admin user. This
would be useful, if you ever wanted to set up the program as a demo for people to explore without creating an account and joining the trading community."

    On the bottom of the main menu, you can see a button says "ENTER AS A DEMO USER", that gives you the option to browse our program as a demo user without having an actual account.

## Functionality 13: "Create another status for accounts besides frozen and unfrozen."

    In this project, we create a status to check if a RegularUser is busy. If a user is in busy, the system will assume that he has limited availability to any trades.
    In this way, this user's willing to lend items will not be shown in the market.

    Log in as an unfrozen regular User. In the regular user menu, click the top right button and then click the VACATION MODE. You can activate your vacation mode.
    After that, go back to the regular user menu and click the icon button at the top left corner. Then click the info button at the top right corner and you can see
    your account status is on vacation mode.

## Functionality 14: "Have the automated trade suggestion always return the most reasonable trade, even if it's not an exact match."

    We have implemented several suggestion algorithms in this project. The tradeSuggestion and smartSuggestion method are used during the trading process, where tradeSuggestion looks for an exact match that is one one user's wish list and another's lending list, and smartSuggestion recommends results based on cosine similarity metric calculated using TF-IDF (Term Frequency - Inverse Document Frequency) scores. 

    In the process of making smart suggestion for the user to select the most reasonable item to lend, we treat the description of each item of the user who needs the suggestion as a "Document". Then we apply the text cleaning process to extract the tokenized words (stop words omitted) from the description. Next we quantify the words using TF-IDF and calculate the cosine similarity between each tokenized description of the user's item and its counterpart, i.e. the other user's item descriptions.

    When marketSuggestion is called when a user browses the market, this returns a list of items that are either newly released or are on the logged in user's wish list. The rankings are based on date, more recent items are ranked higher on the list.

