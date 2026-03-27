import { Component }   from '@angular/core';
import { Router }      from '@angular/router';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';

interface Example {
  icon:  string;
  label: string;
  query: string;
}

@Component({
  selector:   'app-home',
  standalone: true,
  imports:    [CommonModule, RouterModule],
  templateUrl: './home.component.html',
  styleUrls:   ['./home.component.css']
})
export class HomeComponent {

  previewRows = [
    { name: 'Alice Johnson', country: 'USA',       spend: '$3,089.97' },
    { name: 'Carol White',   country: 'UK',        spend: '$2,314.95' },
    { name: 'Grace Kim',     country: 'Canada',    spend: '$1,109.97' },
    { name: 'David Brown',   country: 'Australia', spend: '$2,129.98' },
    { name: 'Eva Martinez',  country: 'USA',       spend: '$999.96'   },
  ];

  examples: Example[] = [
    { icon: '💰', label: 'Top spenders',     query: 'Who are the top 5 customers by total spend?' },
    { icon: '📦', label: 'Best sellers',     query: 'What are the top 3 best-selling products?' },
    { icon: '🌍', label: 'Sales by country', query: 'Show total revenue grouped by customer country' },
    { icon: '📋', label: 'Pending orders',   query: 'List all pending orders with customer names' },
    { icon: '🏷️', label: 'Category revenue', query: 'Show total revenue by product category' },
    { icon: '📅', label: 'Monthly orders',   query: 'How many orders were placed each month in 2024?' },
  ];

  constructor(private router: Router) {}

  tryExample(query: string): void {
    this.router.navigate(['/query'], { queryParams: { q: query } });
  }

  goToQuery(): void {
    this.router.navigate(['/query']);
  }
}
