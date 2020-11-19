#!/usr/bin/env python3

import unittest
from stack import Stack, stack

TEST_SIZE = 1000

def check_match(aStack, aList):
    # this method also uses pop()

    match = True
    i = 0
    while (match and i < len(aList)):
        if (aList[i] != aStack.pop()):
            match = False
        else:
            i += 1
        # end if
    # end while
    
    return match
# end check_match

class stack_test(unittest.TestCase):

    """
    def setUp(self):
        # this method gets called implicitly every
        # time a test in this class gets called

        self.test1 = stack()
    # end setUp
    """

    """
    def tearDown(self):
        # this method gets called implicitly after
        # every test in this class finishes

        self.test1.reset()
    """

    def test_default_constructor(self):
        # this test also uses is_full(),
        # is_empty(), top(), and size()

        self.assertRaises(ValueError, stack, None)
        self.assertRaises(ValueError, stack, 0)
        self.assertRaises(ValueError, stack, -1)

        self.assertRaises(TypeError, stack, "string")
        self.assertRaises(TypeError, stack, 1.0)

        test1 = stack()

        self.assertEqual(test1.size(), 0,
                f"Default size() should be 0, but instead" +
                f" it was {test1.size()}")

        self.assertRaises(RuntimeError, test1.top)

        self.assertTrue(test1.is_empty(),
                f"is_empty() should be True, but instead" +
                f" it was {test1.is_empty()}")
        self.assertFalse(test1.is_full(),
                f"is_full() should be False, but instead" +
                f" it was {test1.is_full()}")

        test1 = stack(100)

        self.assertEqual(test1.size(), 0,
                f"Default size() should be 0, but instead" +
                f" it was {test1.size()}")

        self.assertRaises(RuntimeError, test1.top)

        self.assertTrue(test1.is_empty(),
                f"is_empty() should be True, but instead" +
                f" it was {test1.is_empty()}")
        self.assertFalse(test1.is_full(),
                f"is_full() should be False, but instead" +
                f" it was {test1.is_full()}")
    # end test

    def test_push_and_pop(self):
        test1 = stack(TEST_SIZE)
        test_list = [0] * TEST_SIZE

        for i in range(TEST_SIZE - 1, -1, -1):
            test1.push(i)
            test_list[i] = i
        # end for

        # this check_match() method uses the pop() method
        self.assertTrue(check_match(test1, test_list))

        self.assertRaises(RuntimeError, test1.pop)
    # end test

    def test_empty_and_full(self):
        test1 = stack(TEST_SIZE)

        self.assertTrue(test1.is_empty(),
                f"is_empty() should be True, but instead" +
                f" it was {test1.is_empty()}")
        self.assertFalse(test1.is_full(),
                f"is_full() should be False, but instead" +
                f" it was {test1.is_full()}")

        self.assertRaises(ValueError, test1.push, None)
        self.assertRaises(TypeError, test1.push, 1.0)

        test1.push(0)

        self.assertFalse(test1.is_empty(),
                f"is_empty() should be False, but instead" +
                f" it was {test1.is_empty()}")
        self.assertFalse(test1.is_full(),
                f"is_full() should be False, but instead" +
                f" it was {test1.is_full()}")

        for i in range(1, TEST_SIZE):
            test1.push(i)
        # end for

        self.assertFalse(test1.is_empty(),
                f"is_empty() should be False, but instead" +
                f" it was {test1.is_empty()}")
        self.assertTrue(test1.is_full(),
                f"is_full() should be True, but instead" +
                f" it was {test1.is_full()}")

        self.assertRaises(RuntimeError, test1.push, 0)
    # end test

    def test_size_and_top(self):
        test1 = stack(TEST_SIZE)

        top_bool = True
        size_bool = True
        i = 0
        while(top_bool and size_bool and i < TEST_SIZE):
            test1.push(i)
            if (test1.top() != i):
                top_bool = False
            elif (test1.size() != (i + 1)):
                size_bool = False
            else:
                i += 1
            # end if
        # end while

        self.assertTrue(top_bool, f"top() should be {i}," +
                f" but instead it was {test1.top()}")
        self.assertTrue(size_bool, f"size() should be" +
                f" {i + 1}, but instead it was " +
                f"{test1.top()}")
    # end test

    def test_reset(self):
        test1 = stack(TEST_SIZE)

        for i in range(TEST_SIZE):
            test1.push(i)
        # end for

        self.assertFalse(test1.is_empty(),
                f"is_empty() should be False, but instead" +
                f" it was {test1.is_empty()}")
        self.assertTrue(test1.is_full(),
                f"is_full() should be True, but instead" +
                f" it was {test1.is_full()}")

        test1.reset()

        self.assertEqual(test1.size(), 0,
                f"Default size() should be 0, but instead" +
                f" it was {test1.size()}")

        self.assertRaises(RuntimeError, test1.top)

        self.assertTrue(test1.is_empty(),
                f"is_empty() should be True, but instead" +
                f" it was {test1.is_empty()}")
        self.assertFalse(test1.is_full(),
                f"is_full() should be False, but instead" +
                f" it was {test1.is_full()}")
    # end test
# end class

if __name__ == "__main__":
    unittest.main()
# end if
