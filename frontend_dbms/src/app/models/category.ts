import {CategoryDTO} from "./categoryDTO";

export class Category {
  id: number;
  name: string;

  constructor(data: CategoryDTO) {
    this.id = data.id;
    this.name = data.name;
  }
}
