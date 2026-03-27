import { Component, OnInit }      from '@angular/core';
import { CommonModule }            from '@angular/common';
import { FormsModule }             from '@angular/forms';
import { ActivatedRoute, Router }  from '@angular/router';
import { RouterModule }            from '@angular/router';
import { QueryService }            from '../../services/query.service';
import { AiProvider, QueryResponse } from '../../models/query.model';

interface HistoryItem {
  question: string;
  provider: string;
  response: QueryResponse;
}

@Component({
  selector:    'app-query',
  standalone:  true,
  imports:     [CommonModule, FormsModule, RouterModule],
  templateUrl: './query.component.html',
  styleUrls:   ['./query.component.css']
})
export class QueryComponent implements OnInit {

  question     = '';
  loading      = false;
  inputFocused = false;
  result:    QueryResponse | null = null;
  history:   HistoryItem[]        = [];

  // Provider state
  providers:        AiProvider[] = [];
  selectedProvider  = 'CLAUDE';
  providersLoading  = true;

  providerIcons: Record<string, string> = {
    CLAUDE: '⬡',
    OPENAI: '◎',
    GEMINI: '✦',
    OLLAMA: '⬢',
  };

  suggestions = [
    'Top 5 customers by total spend',
    'Revenue by product category',
    'Pending orders with customer names',
    'Best-selling products',
    'Orders placed each month in 2024',
    'Customers from the USA',
  ];

  constructor(
    private queryService: QueryService,
    private route:        ActivatedRoute,
    private router:       Router
  ) {}

  ngOnInit(): void {
    this.loadProviders();

    this.route.queryParams.subscribe(params => {
      if (params['q']) {
        this.question = params['q'];
        this.submit();
      }
    });
  }

  loadProviders(): void {
    this.queryService.getProviders().subscribe({
      next: (list) => {
        this.providers       = list;
        this.providersLoading = false;
      },
      error: () => {
        // Fallback if backend not reachable yet
        this.providers = [
          { key: 'CLAUDE', displayName: 'Anthropic Claude',     model: 'claude-opus-4-5' },
          { key: 'OPENAI', displayName: 'OpenAI GPT-4o',        model: 'gpt-4o'          },
          { key: 'GEMINI', displayName: 'Google Gemini 1.5 Pro',model: 'gemini-1.5-pro'  },
          { key: 'OLLAMA', displayName: 'Ollama (Local)',        model: 'llama3.2'        },
        ];
        this.providersLoading = false;
      }
    });
  }

  selectProvider(key: string): void {
    this.selectedProvider = key;
  }

  submit(): void {
    const q = this.question.trim();
    if (!q || this.loading) return;

    this.loading = true;
    this.result  = null;

    this.queryService.ask(q, this.selectedProvider).subscribe({
      next: (res) => {
        this.result  = res;
        this.loading = false;
        this.history.unshift({ question: q, provider: this.selectedProvider, response: res });
      },
      error: (err) => {
        this.result  = {
          question: q, provider: this.selectedProvider,
          providerDisplayName: this.getDisplayName(this.selectedProvider),
          columns: [], rows: [], rowCount: 0,
          error: err.message || 'Request failed'
        };
        this.loading = false;
      }
    });
  }

  useSuggestion(s: string): void { this.question = s; this.submit(); }

  useHistory(item: HistoryItem): void {
    this.question         = item.question;
    this.selectedProvider = item.provider;
    this.result           = item.response;
  }

  clearHistory(): void { this.history = []; }

  onKeydown(event: KeyboardEvent): void {
    if (event.key === 'Enter' && !event.shiftKey) {
      event.preventDefault();
      this.submit();
    }
  }

  formatValue(val: unknown): string {
    if (val === null || val === undefined) return '—';
    if (typeof val === 'number') return val.toLocaleString();
    return String(val);
  }

  getIcon(key: string): string { return this.providerIcons[key] ?? '◆'; }

  getDisplayName(key: string): string {
    return this.providers.find(p => p.key === key)?.displayName ?? key;
  }

  getModel(key: string): string {
    return this.providers.find(p => p.key === key)?.model ?? '';
  }

  goHome(): void { this.router.navigate(['/']); }
}
