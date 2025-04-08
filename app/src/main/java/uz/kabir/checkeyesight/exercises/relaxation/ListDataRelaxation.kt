package uz.kabir.checkeyesight.exercises.relaxation


import uz.kabir.checkeyesight.R
import uz.kabir.checkeyesight.exercises.typeconstructor.ExerciseTypes

object ListDataRelaxation {
    fun getExercise():ArrayList<ExerciseTypes>
    {

        val exercises = ArrayList<ExerciseTypes>()

        exercises.add(ExerciseTypes(R.raw.anim_palming, R.string.exercise_palming))
        exercises.add(ExerciseTypes(R.raw.blink_anim, R.string.exercise_blink))
        exercises.add(ExerciseTypes(R.raw.anim_massage_eyebrow, R.string.exercise_massage_eyebrows))
        exercises.add(ExerciseTypes(R.raw.anim_half_closed, R.string.exercise_half_closed))
        exercises.add(ExerciseTypes(R.raw.anim_massage_eyes, R.string.exercise_massage_eyes))
        exercises.add(ExerciseTypes(R.raw.anim_blinking, R.string.exercise_blink_fast))
        exercises.add(ExerciseTypes(R.raw.anim_spinning_head, R.string.exercise_head_spinning))
        exercises.add(ExerciseTypes(R.raw.anim_vertical, R.string.exercise_up_and_down))
        exercises.add(ExerciseTypes(R.raw.anim_horizontal, R.string.exercise_horizontal))
        exercises.add(ExerciseTypes(R.raw.anim_palming, R.string.exercise_palming))


        return exercises
    }
}