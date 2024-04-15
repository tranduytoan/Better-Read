import {User} from "./user";

export interface Review {
  bookId: string;
  userId: string;
  title: string;
  comment: string;
  rating: number;
  createdAt: string;
  user: User;
  expanded: boolean;
}
