import {User} from "./user";
import {Book} from "./book";

export interface ReadingProgress {
  id: number;
  // user: User;
  userId: number;
  bookId: number;
  progress: 'NOT_STARTED' | 'IN_PROGRESS' | 'COMPLETED';
  bookImageUrl: string;
  bookTitle: string;
}
