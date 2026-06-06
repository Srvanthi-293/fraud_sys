/* ──────────────────────────────────────────────────────────────────────────
   Fraud Rule Checker — Frontend
   Flow:
     1. Page load  → GET /api/customers         → fills dropdown
     2. Load click → GET /api/customers/{id}/transactions → shows table
     3. Analyze    → GET /api/fraud/analyze/{txnId}       → shows result card
   ────────────────────────────────────────────────────────────────────────── */

let currentCustomer = null;

// ── On page load: fetch all customers and fill dropdown ───────────────────
window.addEventListener('DOMContentLoaded', async () => {
  try {
    const customers = await getJson('/api/customers');
    const select = document.getElementById('customerSelect');
    customers.forEach(c => {
      const opt = document.createElement('option');
      opt.value = c.id;
      opt.textContent = `${c.id} — ${c.name} (${c.homeCity})`;
      select.appendChild(opt);
    });
  } catch (e) {
    console.error('Failed to load customers:', e);
  }
});

// ── Load Transactions ─────────────────────────────────────────────────────
async function loadTransactions() {
  // Determine customer ID from dropdown or manual input
  const selectVal = document.getElementById('customerSelect').value;
  const inputVal  = document.getElementById('customerIdInput').value.trim();
  const customerId = selectVal || inputVal;

  if (!customerId) {
    alert('Please select a customer or enter a Customer ID.');
    return;
  }

  setLoadingState(true);
  hideResultCard();

  try {
    // Fetch customer info + their transactions in parallel
    const [customer, transactions] = await Promise.all([
      getJson(`/api/customers/${customerId}`),
      getJson(`/api/customers/${customerId}/transactions`)
    ]);

    currentCustomer = customer;
    renderCustomerInfo(customer);
    renderTransactionTable(transactions);

  } catch (e) {
    alert('Error: ' + (e.message || 'Could not load transactions'));
  } finally {
    setLoadingState(false);
  }
}

// ── Render customer info bar ──────────────────────────────────────────────
function renderCustomerInfo(customer) {
  document.getElementById('ci-name').textContent  = '👤 ' + customer.name;
  document.getElementById('ci-city').textContent  = '🏙 ' + customer.homeCity;
  document.getElementById('ci-avg').textContent   = '₹' + customer.averageTransactionAmount.toLocaleString('en-IN');
  document.getElementById('customer-info-bar').classList.remove('hidden');
}

// ── Render transaction table ──────────────────────────────────────────────
function renderTransactionTable(transactions) {
  const tbody = document.getElementById('txn-tbody');
  tbody.innerHTML = '';

  if (!transactions.length) {
    tbody.innerHTML = '<tr><td colspan="8" style="text-align:center;color:#9ca3af;padding:24px">No transactions found.</td></tr>';
    document.getElementById('txn-card').style.display = 'block';
    return;
  }

  transactions.forEach(t => {
    const newPayeeTag = t.newPayee
      ? '<span class="tag-new">New</span>'
      : '<span class="tag-known">Known</span>';

    const timeStr = t.timestamp
      ? new Date(t.timestamp).toLocaleString('en-IN', { dateStyle: 'medium', timeStyle: 'short' })
      : `Hour ${t.transactionHour}:00`;

    const row = document.createElement('tr');
    row.innerHTML = `
      <td><code>#${t.id}</code></td>
      <td><strong>₹${Number(t.amount).toLocaleString('en-IN')}</strong></td>
      <td>${t.payee}</td>
      <td>${newPayeeTag}</td>
      <td>${t.transactionHour}:00</td>
      <td>${t.location}</td>
      <td style="font-size:.78rem;color:#6b7280">${timeStr}</td>
      <td>
        <button class="btn-analyze" onclick="analyzeTransaction(${t.id}, this)">
          🔍 Analyze
        </button>
      </td>`;
    tbody.appendChild(row);
  });

  document.getElementById('txn-card').style.display = 'block';
}

// ── Analyze a transaction ─────────────────────────────────────────────────
async function analyzeTransaction(transactionId, btn) {
  // Show result card in loading state
  const resultCard = document.getElementById('result-card');
  resultCard.style.display = 'block';
  document.getElementById('result-loading').classList.remove('hidden');
  document.getElementById('result-body').classList.add('hidden');
  document.getElementById('result-error').classList.add('hidden');

  // Scroll to result card
  resultCard.scrollIntoView({ behavior: 'smooth', block: 'start' });

  // Disable button while loading
  const origText = btn.textContent;
  btn.disabled = true;
  btn.textContent = '⏳ Analysing…';

  try {
    const data = await getJson(`/api/fraud/analyze/${transactionId}`);
    renderResult(data);
  } catch (e) {
    document.getElementById('result-loading').classList.add('hidden');
    document.getElementById('result-error-msg').textContent = e.message || 'Analysis failed.';
    document.getElementById('result-error').classList.remove('hidden');
  } finally {
    btn.disabled = false;
    btn.textContent = origText;
  }
}

// ── Render fraud analysis result ──────────────────────────────────────────
function renderResult(data) {
  document.getElementById('result-loading').classList.add('hidden');

  const { transactionId, customerName, score, status, reasons, explanationSummary } = data;

  // Status badge
  const badge = document.getElementById('status-badge');
  badge.textContent = status;
  badge.className = 'status-badge ' + status;

  // Score
  document.getElementById('score-value').textContent = score;
  const scoreColor =
    status === 'SAFE'   ? '#059669' :
    status === 'REVIEW' ? '#d97706' : '#dc2626';

  // Score number colour
  document.getElementById('score-value').style.color = scoreColor;

  // Progress bar (max meaningful score = 110)
  const pct = Math.min((score / 110) * 100, 100);
  const bar = document.getElementById('score-bar');
  bar.style.width = pct + '%';
  bar.style.background = scoreColor;

  // Meta
  document.getElementById('r-txnid').textContent    = '#' + transactionId;
  document.getElementById('r-customer').textContent = customerName;

  // Reasons box border colour
  const reasonsBox = document.getElementById('reasons-box');
  reasonsBox.style.background = status === 'SAFE' ? '#f0fdf4' : '#fff7ed';
  reasonsBox.style.borderColor = status === 'SAFE' ? '#86efac' : '#fed7aa';

  // Reasons list
  const list = document.getElementById('reasons-list');
  list.innerHTML = '';

  if (reasons && reasons.length > 0) {
    reasons.forEach(r => {
      const li = document.createElement('li');
      li.textContent = r;
      list.appendChild(li);
    });
    document.getElementById('no-reasons').classList.add('hidden');
    list.classList.remove('hidden');
  } else {
    list.classList.add('hidden');
    document.getElementById('no-reasons').classList.remove('hidden');
  }

  // Summary
  document.getElementById('summary-text').textContent = explanationSummary;

  // Show result
  document.getElementById('result-body').classList.remove('hidden');
}

// ── Helpers ───────────────────────────────────────────────────────────────
async function getJson(url) {
  const res = await fetch(url);
  if (!res.ok) {
    const err = await res.json().catch(() => ({}));
    throw new Error(err.message || `HTTP ${res.status}`);
  }
  return res.json();
}

function setLoadingState(loading) {
  const btn     = document.getElementById('loadBtn');
  const text    = document.getElementById('loadBtnText');
  const spinner = document.getElementById('loadSpinner');
  btn.disabled = loading;
  text.classList.toggle('hidden', loading);
  spinner.classList.toggle('hidden', !loading);
}

function hideResultCard() {
  document.getElementById('result-card').style.display = 'none';
}
