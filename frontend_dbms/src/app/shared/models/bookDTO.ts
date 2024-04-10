import {PublisherDTO} from "./publisherDTO";
import {AuthorDTO} from "./authorDTO";
import {CategoryDTO} from "./categoryDTO";
import {InventoryDTO} from "./inventoryDTO";
import {Inventory} from "./inventory";

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
  categoryIds: number[];
  inventory: Inventory;
}
