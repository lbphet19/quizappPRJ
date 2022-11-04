import { Quiz } from './quiz';
import { Answer } from './answer';
export class Question {
  questionId!:number;
  questionContent!:string;
  questionType!:string;
  answers!:Answer[];
  quiz!:Quiz;
}
