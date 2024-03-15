export interface Book {
  id: string;
  title: string;
  covers: number[];
  latestRevisions: number;
  revision: number;
  author_keys: string[];
  createdAt?: Date;
  updatedAt?: Date;
}
