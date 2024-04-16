import {PublisherDTO} from "./publisherDTO";
import {Inventory} from "./inventory";
import {List} from "postcss/lib/list";
import {CategoryDTO} from "./categoryDTO";

export interface BookDTO {
  id: number;
  title: string;
  isbn: string;
  publisher: PublisherDTO;
  publicationDate: Date;
  language: string;
  pages: number;
  description: string;
  price: number;
  imageUrl: string;
  authorIds: number[];
  category: CategoryDTO[];
  inventory: Inventory;
}
