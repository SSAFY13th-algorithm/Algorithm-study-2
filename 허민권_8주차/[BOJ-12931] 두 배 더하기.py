N = int(input())
arr = list(map(int, input().split()))
count = 0
max_len = 0
for i in arr:
    b = bin(i)[2:]
    count += b.count("1")
    max_len = max(max_len, len(b) - 1)

print(count + max_len)
