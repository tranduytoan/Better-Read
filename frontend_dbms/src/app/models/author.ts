// author.model.ts
import {AuthorDTO} from "./authorDTO";

export class Author {
  id: number;
  name: string;

  constructor(data: AuthorDTO) {
    this.id = data.id;
    this.name = data.name;
  }
}
