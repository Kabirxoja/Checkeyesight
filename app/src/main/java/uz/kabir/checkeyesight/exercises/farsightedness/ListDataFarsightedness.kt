package uz.kabir.checkeyesight.exercises.farsightedness

import uz.kabir.checkeyesight.R
import uz.kabir.checkeyesight.exercises.typeconstructor.ExerciseTypes

object ListDataFarsightedness {
    fun getExercises():ArrayList<ExerciseTypes>
    {

        val exercises = ArrayList<ExerciseTypes>()

        exercises.add(ExerciseTypes(R.raw.anim_half_closed, R.string.exercise_half_closed))
        exercises.add(ExerciseTypes(R.raw.anim_hand_observation, R.string.exercise_hand_observation))
        exercises.add(ExerciseTypes(R.raw.anim_palming, R.string.exercise_palming))
        exercises.add(ExerciseTypes(R.raw.anim_massage_eyebrow, R.string.exercise_massage_eyebrows))
        exercises.add(ExerciseTypes(R.raw.anim_left_rotate, R.string.exercise_rotate_left))
        exercises.add(ExerciseTypes(R.raw.anim_right_rotate, R.string.exercise_rotate_right))
        exercises.add(ExerciseTypes(R.raw.anim_diaganal_1, R.string.exercise_diagonal))
        exercises.add(ExerciseTypes(R.raw.anim_diaganal_2, R.string.exercise_diagonal))
        exercises.add(ExerciseTypes(R.raw.anim_snake, R.string.exercise_snake_trail))
        exercises.add(ExerciseTypes(R.raw.anim_spinning_head, R.string.exercise_head_spinning))


        return exercises
    }
}