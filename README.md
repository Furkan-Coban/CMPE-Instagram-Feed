# Instagram Feed 📱

## 📌 Project Overview
The **Instagram Feed Manager** is a simulation of Instagram’s feed system. It tracks users, posts, likes, and follows while generating personalized feeds efficiently. This project is part of the **CmpE 250: Data Structures and Algorithms** course (Fall 2024).


## ⚙️ Features
- **User Management:** Create users, follow/unfollow others.
- **Post System:** Users can create posts and like/unlike them.
- **Personalized Feed Generation:** Users get feeds based on post popularity.
- **Sorting Mechanism:** Sort posts by likes in descending order.


### Available Commands
| Command | Description |
|---------|------------|
| `create_user <userId>` | Creates a new user. |
| `follow_user <userId1> <userId2>` | User1 follows User2. |
| `unfollow_user <userId1> <userId2>` | User1 unfollows User2. |
| `create_post <userId> <postId> <content>` | Creates a post. |
| `see_post <userId> <postId>` | Marks a post as seen. |
| `toggle_like <userId> <postId>` | Likes/unlikes a post. |
| `generate_feed <userId> <num>` | Generates a feed for a user. |
| `scroll_through_feed <userId> <num> <likes>` | Scrolls through feed and likes/unlikes posts. |
| `sort_posts <userId>` | Sorts a user’s posts by likes. |

## 🚀 How to Run
1. **Compile the code:**
   ```sh
   javac *.java
2. **Run the program with input/output files:**
   ```sh
   java Main <input_file> <output_file>

## 🛠️ Technologies Used
- Java
- File I/O Handling
- Data Structures (Graphs, Heaps, Lists, etc.)
- Algorithm Optimization

## 📌 Author
- Name: Furkan Çoban
- Course: CmpE 250 - Boğaziçi University
- Year: Fall 2024


