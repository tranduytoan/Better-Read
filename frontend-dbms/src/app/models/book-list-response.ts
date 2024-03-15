import {Book} from "./book";

export interface BookListResponse {
  books: Book[];
  totalPages: number;
}
