package uz.kabir.checkeyesight.visiontest

import uz.kabir.checkeyesight.R
import java.util.*

object List1 {
    fun getQuestions1(): ArrayList<Question> {

        //create ArrayList of type question to store data
        val questionsList = ArrayList<Question>()

        //populate the questions
        val que1 = Question(
            1,
            R.drawable.letter_d,
            listOf(
                R.drawable.letter_d,
                R.drawable.letter_e,
                R.drawable.letter_c,
                R.drawable.letter_g
            )
        )
        //que1.variants.shuffled()
        questionsList.add(que1)

        val que2 = Question(
            2,
            R.drawable.letter_p,
            listOf(
                R.drawable.letter_b,
                R.drawable.letter_r,
                R.drawable.letter_p,
                R.drawable.letter_h
            )
        )
        //que2.variants.shuffled()
        questionsList.add(que2)

        val que3 = Question(
            3,
            R.drawable.letter_m,
            listOf(
                R.drawable.letter_v,
                R.drawable.letter_n,
                R.drawable.letter_x,
                R.drawable.letter_m
            )
        )
        //que3.variants.shuffled()

        questionsList.add(que3)

        val que4 = Question(
            4,
            R.drawable.letter_s,
            listOf(
                R.drawable.letter_s,
                R.drawable.letter_f,
                R.drawable.letter_o,
                R.drawable.letter_b
            )
        )
        //que4.variants.shuffled()

        questionsList.add(que4)

        val que5 = Question(
            5,
            R.drawable.letter_h,
            listOf(
                R.drawable.letter_x,
                R.drawable.letter_u,
                R.drawable.letter_h,
                R.drawable.letter_j
            )
        )
        // que5.variants.shuffled()
        questionsList.add(que5)

        val que6 = Question(
            6,
            R.drawable.letter_q,
            listOf(
                R.drawable.letter_o,
                R.drawable.letter_c,
                R.drawable.letter_q,
                R.drawable.letter_g
            )
        )
        //  que6.variants.shuffled()

        questionsList.add(que6)

        val que7 = Question(
            7,
            R.drawable.letter_w,
            listOf(
                R.drawable.letter_f,
                R.drawable.letter_y,
                R.drawable.letter_v,
                R.drawable.letter_w
            )
        )
        //que7.variants.shuffled()

        questionsList.add(que7)

        val que8 = Question(
            8,
            R.drawable.letter_b,
            listOf(
                R.drawable.letter_b,
                R.drawable.letter_l,
                R.drawable.letter_f,
                R.drawable.letter_e
            )

        )
        //que8.variants.shuffled()
        questionsList.add(que8)


        val que9 = Question(
            9,
            R.drawable.letter_f,
            listOf(
                R.drawable.letter_e,
                R.drawable.letter_k,
                R.drawable.letter_f,
                R.drawable.letter_y
            )
        )
        //que9.variants.shuffled()

        questionsList.add(que9)

        val que10 = Question(
            10,
            R.drawable.digit_one,
            listOf(
                R.drawable.letter_i,
                R.drawable.letter_l,
                R.drawable.digit_zero,
                R.drawable.digit_one
            )
        )
        //que10.variants.shuffled()

        questionsList.add(que10)

//        questionsList.shuffle(Random(10))

        return  questionsList
    }

}