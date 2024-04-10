export interface Book {
  id: number;
  title: string;
  isbn: string;
  publisherId: number;
  publicationDate: Date;
  language: string;
  pages: number;
  description: string;
  price: number;
  imageUrl: string;
  authorIds: number[];
  categoryIds: number[];
  // inventoryId: number;
}
