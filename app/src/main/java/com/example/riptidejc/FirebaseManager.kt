package com.example.riptidejc

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class FirebaseManager {

    private var database = Firebase.database.reference
    private var currentUser = Firebase.auth.currentUser

    init {
        //firebase reference initialization
        database = Firebase.database.reference
    }

    fun createUser(userId: String, email: String, name: String, role: String) {
        // Use the user ID as the key for the user data in the Realtime Database
        val userRef = FirebaseDatabase.getInstance().reference.child("users").child(userId)

        // Create a user object or map with the relevant user data
        val userData = User(name = name, email = email, role = role)

        // Set the user data in the Realtime Database using the user ID as the key
        userRef.setValue(userData)
    }

    interface CreateCourseListener {
        fun onSuccess(courseId: String)
        fun onError(databaseError: DatabaseError)
    }
    fun createCourse(name: String, teacherId: String, listener: CreateCourseListener) {
        val courseData = Course(name = name, teacherId = teacherId)
        val courseRef = database.child("courses").push()
        val courseId = courseRef.key.toString()
        courseRef.setValue(courseData)
        listener.onSuccess(courseId)
    }

    interface GetUserRoleListener {
        fun onSuccess(role: String)
        fun onError(databaseError: DatabaseError)
    }

    fun getUserRole(uid: String, listener: GetUserRoleListener) {
        Log.d("FM GetUserRole", "uid: $uid")
        val roleRef = database.child("users").child(uid).child("role")

        roleRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val role = dataSnapshot.getValue(String::class.java)
                Log.d("FM GetUserRole", "Role: ${role.toString()}")
                // Use the role value as needed
                listener.onSuccess(role.toString())
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Handle error
            }
        })

    }



    fun postQuestion(course: String, title: String, body: String) {

        val questionData = Question(title, body, course, currentUser?.uid)
        database.child("questions").push().setValue(questionData)
    }

    interface GetQuestionsByUserListener {
        fun onSuccess(questions: List<Pair<String,Question>>)
        fun onError(databaseError: DatabaseError)
    }

    fun getQuestionsByUser(listener: GetQuestionsByUserListener) {
        val questionList = mutableListOf<Pair<String,Question>>()

        val questionQuery = database.child("questions").orderByChild("user").equalTo(currentUser?.uid)
        questionQuery.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                for (questionSnapshot in dataSnapshot.children) {
                    val question = questionSnapshot.getValue(Question::class.java)
                    val questionId = questionSnapshot.key.toString()
                    Log.d("GetQuestionsByUser", "Hit question")
                    if (question != null) {
                        questionList.add(Pair(questionId, question))
                    }
                }
                listener.onSuccess(questionList)
            }

            override fun onCancelled(error: DatabaseError) {
                Log.d("GetQuestionsByUser", "Fail")
            }
        })
    }

    interface GetAllQuestionsListener {
        fun onSuccess(questions: List<Question>)
        fun onError(databaseError: DatabaseError)
    }
    fun getAllQuestions(listener: GetAllQuestionsListener) {
        val questionList = mutableListOf<Question>()

        val questionQuery = database.child("questions")
        questionQuery.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                for (questionSnapshot in dataSnapshot.children) {
                    val question = questionSnapshot.getValue(Question::class.java)
                    Log.d("GetAllQuestions", "Hit question")
                    if (question != null) {
                        questionList.add(question)
                    }
                }
                listener.onSuccess(questionList)
            }

            override fun onCancelled(error: DatabaseError) {
                Log.d("GetQuestionsByUser", "Fail")
            }
        })
    }

    interface GetCoursesByTeacherListener {
        fun onSuccess(courses: List<Course>)
        fun onError(databaseError: DatabaseError)
    }
    fun getCoursesByTeacher(listener: GetCoursesByTeacherListener) {
        val query = database
            .child("courses")
            .orderByChild("teacherId")
            .equalTo(currentUser?.uid)

        query.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val coursesList = mutableListOf<Course>()

                for (courseSnapshot in dataSnapshot.children) {
                    val course = courseSnapshot.getValue(Course::class.java)

                    if (course != null)
                        coursesList.add(course)
                }
                listener.onSuccess(coursesList)

                // Handle the list of courses as needed
                // e.g., pass it to an adapter, display the courses, etc.
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Handle error
            }
        })
    }

    interface GetStudentEnrollmentsListener {
        fun onSuccess(courses: List<Pair<String, Course>>)
        fun onError(databaseError: DatabaseError)
    }
    fun getStudentEnrollments(listener: GetStudentEnrollmentsListener) {
        val enrollmentsRef = database.child("enrollments")
        val query = enrollmentsRef.orderByChild("studentId").equalTo(currentUser?.uid.toString())

        query.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val courses = mutableListOf<Pair<String, Course>>()

                for (enrollmentSnapshot in dataSnapshot.children) {
                    val enrollment = enrollmentSnapshot.getValue(Enrollment::class.java)
                    val courseId = enrollment?.courseId

                    if (courseId != null) {
                        val coursesRef = database.child("courses")
                        val courseQuery = coursesRef.child(courseId)

                        courseQuery.addListenerForSingleValueEvent(object : ValueEventListener {
                            override fun onDataChange(courseDataSnapshot: DataSnapshot) {
                                val course = courseDataSnapshot.getValue(Course::class.java)
                                if (course != null) {
                                    courses.add(Pair(courseId, course))
                                }
                                if (courses.size == dataSnapshot.childrenCount.toInt()) {
                                    listener.onSuccess(courses)
                                }
                            }

                            override fun onCancelled(databaseError: DatabaseError) {
                                // Handle database error, if needed

                            }
                        })
                    }
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Handle database error, if needed

            }
        })
    }

    interface GetQuestionByIdListener {
        fun onSuccess(question: Question)
        fun onError(databaseError: DatabaseError)
    }

    fun getQuestionById(questionId: String, listener: GetQuestionByIdListener) {
        val database = FirebaseDatabase.getInstance()
        val questionsRef = database.getReference("questions")

        questionsRef.child(questionId).addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val question = snapshot.getValue(Question::class.java)
                if (question != null) {
                    listener.onSuccess(question)
                }
            }

            override fun onCancelled(error: DatabaseError) {
                listener.onError(error)
            }
        })
    }



    fun addStudentsToCourse(emailList: ArrayList<String>, courseId: String) {
        // Iterate through each email in the list
        for (email in emailList) {
            // Search the 'user' Firebase collection for users with matching 'email' attribute
            val query = database.child("users")
                .orderByChild("email").equalTo(email)

            query.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    // Iterate through the dataSnapshot to retrieve matching users
                    for (userSnapshot in dataSnapshot.children) {
                        // Get the user ID
                        val userId = userSnapshot.key

                        // Create an Enrollment object
                        val enrollment = Enrollment(courseId = courseId, studentId = userId)

                        // Push the enrollment into the 'Enrollments' collection
                        database.child("enrollments").push().setValue(enrollment)
                    }
                }

                override fun onCancelled(databaseError: DatabaseError) {
                    // Handle database error, if needed
                }
            })
        }
    }

}
