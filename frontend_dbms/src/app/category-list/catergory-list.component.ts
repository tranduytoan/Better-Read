import {Component, OnInit} from '@angular/core';
import {Category} from "../models/category";
import {CategoryService} from "../services/category.service";

@Component({
  selector: 'app-category-list',
  templateUrl: './catergory-list.component.html',
  styleUrl: './catergory-list.component.css'
})
export class CatergoryListComponent implements OnInit{
  categories: Category[] = [];
  constructor(private categoryService: CategoryService) {
  }
  ngOnInit() {
    this.getCategories();
  }

  getCategories() {
    this.categoryService.getAllCategories().subscribe(
      (categories: Category[]) => {
        this.categories = categories;
      },
      error => {
        console.log('Error retrieving categories', error);
      }
    );
  }
}
