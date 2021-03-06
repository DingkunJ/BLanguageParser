﻿using System;
using System.Collections;

public class StartsWith : Function
{
    public virtual string Name
    {
        get
        {
            return "startsWith";
        }
    }

    public virtual FunctionResult execute(Evaluator evaluator, string arguments)
    {
        string result = null;
        string exceptionMessage = "Two string arguments and one integer " + "argument are required.";
        ArrayList values = FunctionHelper.getTwoStringsAndOneInteger(arguments, EvaluationConstants.FUNCTION_ARGUMENT_SEPARATOR);
        if (values.Count != 3)
        {
            throw new FunctionException(exceptionMessage);
        }
        try
        {
            string argumentOne = FunctionHelper.trimAndRemoveQuoteChars((string)values[0], evaluator.QuoteCharacter);
            string argumentTwo = FunctionHelper.trimAndRemoveQuoteChars((string)values[1], evaluator.QuoteCharacter);
            int index = ((int?)values[2]).Value;
            if (argumentOne.StartsWith(argumentTwo, index))
            {
                result = EvaluationConstants.BOOLEAN_STRING_TRUE;
            }
            else
            {
                result = EvaluationConstants.BOOLEAN_STRING_FALSE;
            }
        }
        catch (FunctionException fe)
        {
            throw new FunctionException(fe.Message, fe);
        }
        catch (Exception e)
        {
            throw new FunctionException(exceptionMessage, e);
        }
        return new FunctionResult(result, FunctionConstants.FUNCTION_RESULT_TYPE_NUMERIC);
    }
}
