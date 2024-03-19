import {Component, OnInit} from '@angular/core';
import {Userbooks} from "../models/userbooks";
import {UserBooksService} from "../services/user-books.service";
import {ActivatedRoute} from "@angular/router";
import {Observable} from "rxjs";

@Component({
  selector: 'app-user-books',
  templateUrl: './user-books.component.html',
  styleUrls: ['./user-books.component.scss']
})
export class UserBooksComponent implements OnInit{
  userBooks: Userbooks[] = [];
  userId ='123';
  constructor(
    private userBookService: UserBooksService,
    private route: ActivatedRoute
  ) { }
  ngOnInit(): void {
    const userId = this.route.snapshot.paramMap.get('id');
    if (userId) {
      this.userBookService.getUserBooks(userId).subscribe(userBooks => {
        this.userBooks = userBooks;
      });
    }
  }

  markAsRead(bookId: string) {
    this.userBookService.markAsRead(bookId, this.userId).subscribe({
      next: () => {
        // this.loadUserBooks();
      },
      error: (error) => console.error('There was an error!', error)
    });
  }

  removeBook(bookId: string) {
    this.userBookService.removeBook(bookId, this.userId).subscribe({
      next: () => {
        // this.loadUserBooks();
      },
      error: (error) => console.error('There was an error!', error)
    });
  }

  loadUserBooks(userId: string): void {
    this.userBookService.getUserBooks(userId).subscribe(userBooks => {
      this.userBooks = userBooks;
    })
  }
}
