def sameValues(arr1, arr2):
    for i in range(len(arr1)):
        same = False
        for j in range(len(arr2)):
            if arr1[i] == arr2[j]:
                same = True
                break

        if not same:
            return False

    for i in range(len(arr2)):
        same = False
        for j in range(len(arr1)):
            if arr2[i] == arr1[j]:
                same = True
                break

        if not same:
            return False

    return True


arr1 = [1, 2, 3]
arr2 = [1, 2, 3, 3, 2]
arr3 = [1, 4, 3, 2]

print(sameValues(arr1, arr2))
print(sameValues(arr1, arr3))
print(sameValues(arr2, arr3))
