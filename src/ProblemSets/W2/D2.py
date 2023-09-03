"""
Given a chessboard, there is a configuration of 8 queens in which each queen
does not threaten any other queen.
Using a backtrace, find that configuration.
Return the configuration as an array of 8 integers,
where each value represents the column (0-7)
and each index represents the row (0-7).
"""


def findAllConfigs():
    """
    Finds all possible configurations of 8 queens on a chessboard
    where no queen threatens any other queen.
    """
    configs = []

    return configs


def threatens(config, row, col):
    """
    Returns True if the queen at (row, col) threatens any other queen
    in the given configuration.
    """
    for i in range(len(config)):
        if config[i] == col or abs(i - row) == abs(config[i] - col):
            return True
    return False


def printAsBoard(config):
    """
    Prints the given configuration as a chessboard.
    """
    for row in range(8):
        print("|", end="")
        for col in range(8):
            if config[row] == col:
                print("*|", end="")
            else:
                print("_|", end="")
        print()

    print(config)
    print()


findAllConfigs()
