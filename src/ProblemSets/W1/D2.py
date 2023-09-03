arr1 = [1, 2, 3, 4, 5]
arr2 = [5, 4, 3, 4, 5, 4, 3, 4, 5]
arr3 = [9, 8, 7, 6, 5, 4, 3, 4, 5, 6, 7, 8, 9, 10]
arr4 = [7, 3, 4, -5, 6, 7, 8, 2, 3, -4, 5, 6, 7]


def minValue(arr):
    minValue = arr[0]
    for i in range(1, len(arr)):
        if arr[i] < minValue:
            minValue = arr[i]
    return minValue


def maxValue(arr):
    maxValue = arr[0]
    for i in range(1, len(arr)):
        if arr[i] > maxValue:
            maxValue = arr[i]
    return maxValue


def findMaxBetweenNegatives(arr):
    lowerIndex = -1
    # upperIndex = -1
    maxValue = -1
    for i in range(0, len(arr)):
        if arr[i] < 0:
            lowerIndex = i
            break

    for i in range(lowerIndex + 1, len(arr)):
        if arr[i] < 0:
            break
        elif arr[i] > maxValue:
            maxValue = arr[i]

    return maxValue


def findSecondMax(arr):
    workArr = arr.copy()
    workArr.remove(maxValue(workArr))
    return maxValue(workArr)


print(findSecondMax(arr4))
