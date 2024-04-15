import {CategoryDTO} from "./categoryDTO";

export class Category {
  id: number;
  name: string;
  parent: number | null;
  constructor(data: CategoryDTO) {
    this.id = data.id;
    this.name = data.name;
    this.parent = data.parent;
  }
}
