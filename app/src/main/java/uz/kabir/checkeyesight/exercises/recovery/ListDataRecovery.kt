package uz.kabir.checkeyesight.exercises.recovery

import uz.kabir.checkeyesight.R
import uz.kabir.checkeyesight.exercises.typeconstructor.ExerciseTypes

object ListDataRecovery {

    fun getExercise():ArrayList<ExerciseTypes>
    {

        val exercises = ArrayList<ExerciseTypes>()

        exercises.add(ExerciseTypes(R.raw.anim_vertical,R.string.exercise_up_and_down))
        exercises.add(ExerciseTypes(R.raw.anim_diaganal_1,R.string.exercise_diagonal))
        exercises.add(ExerciseTypes(R.raw.anim_left_near,R.string.exercise_left_near))
        exercises.add(ExerciseTypes(R.raw.anim_right_near,R.string.exercise_right_near))
        exercises.add(ExerciseTypes(R.raw.anim_diaganal_2,R.string.exercise_diagonal))
        exercises.add(ExerciseTypes(R.raw.anim_snake,R.string.exercise_snake_trail))
        exercises.add(ExerciseTypes(R.raw.anim_blinking,R.string.exercise_blink_fast))
        exercises.add(ExerciseTypes(R.raw.anim_blinking,R.string.exercise_blink))
        exercises.add(ExerciseTypes(R.raw.anim_palming,R.string.exercise_palming))

        return exercises
    }
}