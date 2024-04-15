import {Category} from "./category";

export interface CategoryDTO {
  id: number;
  name: string;
  parent: number | null;
}
