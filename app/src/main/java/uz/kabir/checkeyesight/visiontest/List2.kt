package uz.kabir.checkeyesight.visiontest


import uz.kabir.checkeyesight.R
import java.util.ArrayList

object List2 {

    const val USER_NAME: String = "user_name"
    const val TOTAL_QUESTIONS: String = "total_questions"
    const val CORRECT_ANSWERS1: String = "correct_answers1"
    const val CORRECT_ANSWERS2: String = "correct_answers2"

    fun getQuestions2(): ArrayList<Question> {

        //create ArrayList of type question to store data
        val questionsList = ArrayList<Question>()

        //populate the questions
        val que1 = Question(
            1,
            R.drawable.digit_seven,
            listOf(
                R.drawable.digit_one,
                R.drawable.digit_seven,
                R.drawable.digit_four,
                R.drawable.digit_three
            )
        )
        //que1.variants.shuffled()
        questionsList.add(que1)

        val que2 = Question(
            2,
            R.drawable.digit_eight,
            listOf(
                R.drawable.digit_eight,
                R.drawable.digit_three,
                R.drawable.digit_six,
                R.drawable.digit_five
            )
        )
        //que2.variants.shuffled()
        questionsList.add(que2)

        val que3 = Question(
            3,
            R.drawable.digit_nine,
            listOf(
                R.drawable.digit_six,
                R.drawable.digit_eight,
                R.drawable.digit_nine,
                R.drawable.digit_three
            )
        )
        //que3.variants.shuffled()

        questionsList.add(que3)

        val que4 = Question(
            4,
            R.drawable.digit_zero,
            listOf(
                R.drawable.digit_eight,
                R.drawable.digit_zero,
                R.drawable.digit_two,
                R.drawable.digit_five
            )
        )
        //que4.variants.shuffled()

        questionsList.add(que4)

        val que5 = Question(
            5,
            R.drawable.digit_one,
            listOf(
                R.drawable.digit_seven,
                R.drawable.digit_two,
                R.drawable.digit_one,
                R.drawable.digit_four
            )
        )
        // que5.variants.shuffled()
        questionsList.add(que5)

        val que6 = Question(
            6,
            R.drawable.digit_six,
            listOf(
                R.drawable.digit_six,
                R.drawable.digit_seven,
                R.drawable.digit_eight,
                R.drawable.digit_five
            )
        )
        //  que6.variants.shuffled()

        questionsList.add(que6)

        val que7 = Question(
            7,
            R.drawable.digit_three,
            listOf(
                R.drawable.digit_four,
                R.drawable.digit_five,
                R.drawable.digit_nine,
                R.drawable.digit_three
            )
        )
        //que7.variants.shuffled()

        questionsList.add(que7)

        val que8 = Question(
            8,
            R.drawable.digit_five,
            listOf(
                R.drawable.digit_zero,
                R.drawable.digit_six,
                R.drawable.digit_five,
                R.drawable.digit_two
            )

        )
        //que8.variants.shuffled()
        questionsList.add(que8)


        val que9 = Question(
            9,
            R.drawable.digit_four,
            listOf(
                R.drawable.digit_one,
                R.drawable.digit_seven,
                R.drawable.digit_four,
                R.drawable.digit_eight
            )
        )
        //que9.variants.shuffled()

        questionsList.add(que9)

        val que10 = Question(
            10,
            R.drawable.digit_two,
            listOf(
                R.drawable.digit_five,
                R.drawable.digit_seven,
                R.drawable.digit_three,
                R.drawable.digit_two
            )
        )
        //que10.variants.shuffled()

        questionsList.add(que10)

//        questionsList.shuffle(Random(10))

        return  questionsList
    }

}