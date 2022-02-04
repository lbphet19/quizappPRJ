import { Question } from './question';
export class Answer {
  answerId!:number;
  answerContent!:string;
  answerIsCorrect!:boolean;
  question!:Question
}
