package uz.kabir.checkeyesight.exercises.nearsightedness

import uz.kabir.checkeyesight.R
import uz.kabir.checkeyesight.exercises.typeconstructor.ExerciseTypes

object ListDataNearsightedness {

    fun getExercises():ArrayList<ExerciseTypes>
    {

        val exercises = ArrayList<ExerciseTypes>()

        exercises.add(ExerciseTypes(R.raw.anim_look_noose, R.string.exercise_focus_nose))
        exercises.add(ExerciseTypes(R.raw.anim_left_near, R.string.exercise_left_near))
        exercises.add(ExerciseTypes(R.raw.anim_massage_eyebrow, R.string.exercise_massage_eyebrows))
        exercises.add(ExerciseTypes(R.raw.anim_right_near, R.string.exercise_right_near))
        exercises.add(ExerciseTypes(R.raw.anim_half_closed, R.string.exercise_half_closed))
        exercises.add(ExerciseTypes(R.raw.anim_diaganal_1, R.string.exercise_diagonal))
        exercises.add(ExerciseTypes(R.raw.anim_diaganal_2, R.string.exercise_diagonal))
        exercises.add(ExerciseTypes(R.raw.anim_rectangle, R.string.exercise_rectangle))
        exercises.add(ExerciseTypes(R.raw.anim_spinning_head, R.string.exercise_head_spinning))
        exercises.add(ExerciseTypes(R.raw.anim_palming, R.string.exercise_palming))

        return exercises
    }
}