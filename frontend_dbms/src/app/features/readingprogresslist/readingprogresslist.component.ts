import { Component, OnInit } from '@angular/core';
import { ReadingProgress } from "../../shared/models/readingprogress";
import { ReadingProgressService } from "../../core/services/readingprogress.service";
import { UserService } from "../../core/services/user.service";
import {Router} from "@angular/router";

@Component({
  selector: 'app-readingprogresslist',
  templateUrl: './readingprogresslist.component.html',
  styleUrls: ['./readingprogresslist.component.css']
})
export class ReadingProgressListComponent implements OnInit {
  readingProgressList: ReadingProgress[] = [];
  currentPage = 0;
  pageSize = 10;
  userId!: number | null;

  constructor(private readingProgressService: ReadingProgressService,
              private userService: UserService,
              private router: Router) { }

  ngOnInit(): void {
    this.userService.userId$.subscribe(userId => {
      this.userId = userId;
      if (this.userId) {
        this.loadReadingProgress();
      }
    });
  }

  loadReadingProgress(): void {
    if (this.userId) {
      this.readingProgressService.getReadingProgressByUserId(this.userId, this.currentPage, this.pageSize)
        .subscribe((response: ReadingProgress[]) => {
          this.readingProgressList = response
          console.log(this.readingProgressList);
        }, error => {
          console.error('Failed to load reading progress:', error);
        });
    }
  }

  updateReadingProgress(bookId: number, progress: string): void {
    if (this.userId) {
      const request: Partial<ReadingProgress> = {
        userId: this.userId,
        bookId,
        progress: progress as 'NOT_STARTED' | 'IN_PROGRESS' | 'COMPLETED',
      };
      this.readingProgressService.updateReadingProgress(bookId, request)
        .subscribe(() => {
          this.loadReadingProgress();
        }, error => {
          console.error('Failed to update reading progress:', error);
        });
    }
  }

  addBookToReadingProgress(bookId: number): void {
    if (this.userId) {
      const request: Partial<ReadingProgress> = {
        userId: this.userId,
        bookId,
        progress: 'NOT_STARTED',
      };
      this.readingProgressService.addBookToReadingProgress(request)
        .subscribe(() => {
          this.loadReadingProgress();
        }, error => {
          console.error('Failed to add book to reading progress:', error);
        });
    }
  }

  goToBookDetail(bookId: number) {
    this.router.navigate(['/book', bookId]);
  }
}
