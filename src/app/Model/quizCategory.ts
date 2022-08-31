import { QuizResponseDTO } from './QuizResponseDTO';
export class QuizCategory {
   catId!:string
   catName!:string
   quizzes!:QuizResponseDTO[]
}
